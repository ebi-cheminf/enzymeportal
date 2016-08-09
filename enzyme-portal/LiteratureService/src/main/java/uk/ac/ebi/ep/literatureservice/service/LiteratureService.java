package uk.ac.ebi.ep.literatureservice.service;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import uk.ac.ebi.ep.literatureservice.model.EuropePMC;
import uk.ac.ebi.ep.literatureservice.model.Result;
import uk.ac.ebi.ep.uniprotservice.transferObjects.CitationLabel;
import uk.ac.ebi.ep.uniprotservice.transferObjects.LabelledCitation;

/**
 *
 * @author joseph
 */
@Service
public class LiteratureService {

    private final PmcRestService pmcRestService;
    private static final Comparator<LabelledCitation> SORT_BY_DATE_ASC = (LabelledCitation key1, LabelledCitation key2) -> -(key1.getCitation().getDateOfCreation().compareTo(key2.getCitation().getDateOfCreation()));

    public LiteratureService(PmcRestService pmcRestService) {
        this.pmcRestService = pmcRestService;
    }

    private Optional<EuropePMC> getEuropePmcCitationsByAccession(String accession, int limit) {

        return pmcRestService.findPublicationsByAccession(accession, limit);

    }

    private Optional<EuropePMC> citationsByKeyword(String keyword) {

        return pmcRestService.findPublicationsByKeyword(keyword);
    }

    public EuropePMC getEuropePmcCitationsByKeyword(String keyword) {

        return citationsByKeyword(keyword).orElse(new EuropePMC());

    }

    /**
     * This method assumes that the term is an accession and makes a search to
     * the Pubmed service, if no result if found, term is assumed to be a
     * keyword.
     *
     *
     * @param term could be accession or keyword
     * @param limit max number of citations to find
     * @return List of LabelledCitation
     */
    public List<LabelledCitation> getCitations(String term, int limit) {

        EuropePMC pmc = getEuropePmcCitationsByAccession(term, limit).orElse(getEuropePmcCitationsByKeyword(term));

        List<LabelledCitation> citations = computeLabelledCitation(pmc);
        return citations.stream().distinct().sorted(SORT_BY_DATE_ASC).limit(limit).collect(Collectors.toList());

    }

    private List<LabelledCitation> computeLabelledCitation(EuropePMC pmc) {
        List<LabelledCitation> citations = new LinkedList<>();
        pmc.getResultList().getResult().stream().forEach(result -> {
            EnumSet<CitationLabel> labels = getLabels(result);
            LabelledCitation citation = new LabelledCitation(result, labels);
//add only citations with label
            if (citations.contains(citation)) {
                if (!citation.getLabels().isEmpty()) {
                    citations.get(citations.indexOf(citation))
                            .addLabels(labels);
                }
            } else {
                if (!citation.getLabels().isEmpty()) {
                    citations.add(citation);
                }
            }
        });

        return citations;
    }

    /**
     * Chooses the proper labels for a citation according to the database
     * cross-references it has got.
     *
     * @param citation a citation from PudMed.
     * @return a set of labels appropriate for the citation.
     */
    private EnumSet<CitationLabel> getLabels(Result citation) {
        EnumSet<CitationLabel> labels = EnumSet.noneOf(CitationLabel.class);

        if (citation.getDbCrossReferenceList() != null) {
            citation.getDbCrossReferenceList().getDbName().stream()
                    .map(dbName -> Optional.ofNullable(CitationLabel.forDatabase(dbName)))
                    .filter(label -> label.isPresent())
                    .forEach(label -> labels.add(label.get()));
        }
        return labels;
    }

}
