package uk.ac.ebi.ep.xml.transformer;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.jpa.QueryHints;
import org.hibernate.query.Query;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import uk.ac.ebi.ep.xml.dao.FieldAndRef;
import uk.ac.ebi.ep.xml.entities.PrimaryProtein;
import uk.ac.ebi.ep.xml.entities.ProteinGroups;
import uk.ac.ebi.ep.xml.entities.ProteinXml;
import uk.ac.ebi.ep.xml.entities.repositories.ProteinXmlRepository;
import uk.ac.ebi.ep.xml.schema.AdditionalFields;
import uk.ac.ebi.ep.xml.schema.CrossReferences;
import uk.ac.ebi.ep.xml.schema.Entry;
import uk.ac.ebi.ep.xml.schema.Field;
import uk.ac.ebi.ep.xml.schema.Ref;
import uk.ac.ebi.ep.xml.util.DatabaseName;
import uk.ac.ebi.ep.xml.util.FieldName;

/**
 *
 * @author Joseph <joseph@ebi.ac.uk>
 */
@Slf4j
public class ProteinGroupsProcessor extends XmlTransformer implements ItemProcessor<ProteinGroups, Entry> {

    protected static final String REVIEWED = "reviewed";
    protected static final String UNREVIEWED = "unreviewed";
   // private final AtomicInteger count = new AtomicInteger(1);

    private final ProteinXmlRepository proteinXmlRepository;
    private final EntityManagerFactory entityManagerFactory;

    public ProteinGroupsProcessor(ProteinXmlRepository repository, EntityManagerFactory emf) {
        this.proteinXmlRepository = repository;
        this.entityManagerFactory = emf;
    }

    private void addPrimaryProteinField(PrimaryProtein primaryProtein, Set<Field> fields) {

        String accession = primaryProtein.getAccession();
        String commonName = primaryProtein.getCommonName();
        if (commonName == null || commonName.isEmpty()) {
            commonName = primaryProtein.getScientificName();
        }
        Field primaryOganismfield = new Field(FieldName.PRIMARY_ORGANISM.getName(), commonName);
        fields.add(primaryOganismfield);
        Field primaryAccessionfield = new Field(FieldName.PRIMARY_ACCESSION.getName(), accession);
        fields.add(primaryAccessionfield);

    }

    private void addPrimaryFunctionFields(PrimaryProtein primaryProtein, Set<Field> fields) {

        if (primaryProtein.getFunction() != null && !StringUtils.isEmpty(primaryProtein.getFunction())) {
            Field primaryFunctionfield = new Field(FieldName.FUNCTION.getName(), primaryProtein.getFunction());

            fields.add(primaryFunctionfield);
        }

    }

    private void addPrimaryImage(PrimaryProtein primaryProtein, Set<Field> fields) {

        Character hasPdbFlag = 'Y';

        if (primaryProtein.getPdbFlag().equals(hasPdbFlag)) {
            String pdbId = primaryProtein.getPdbId() + "|" + primaryProtein.getPdbSpecies() + "|" + primaryProtein.getPdbLinkedAcc();
            Field pdbfield = new Field(FieldName.PRIMARY_IMAGE.getName(), pdbId);
            fields.add(pdbfield);
        }

    }

    private void addEntryTypeFields(PrimaryProtein primaryProtein, Set<Field> fields) {

        BigInteger type = primaryProtein.getEntryType();

        if (type != null) {
            Field entryTypefield = new Field(FieldName.ENTRY_TYPE.getName(), "" + type.intValue());

            fields.add(entryTypefield);
        }

    }

    @Override
    public Entry process(ProteinGroups proteinGroups) throws Exception {
        AdditionalFields additionalFields = new AdditionalFields();
        CrossReferences cr = new CrossReferences();
//        CopyOnWriteArraySet<Field> fields = new CopyOnWriteArraySet<>();
//        CopyOnWriteArraySet<Ref> refs = new CopyOnWriteArraySet<>();
//        CopyOnWriteArraySet<String> relSpecies = new CopyOnWriteArraySet<>();

        Set<Field> fields = new HashSet<>();
        Set<Ref> refs = new HashSet<>();
        Set<String> relSpecies = new HashSet<>();
        //log.info("Processor " + Runtime.getRuntime().availableProcessors() + " current entry : " + proteinGroups.getProteinGroupId() + "  entry count : " + count.getAndIncrement());

        Entry entry = new Entry();

        entry.setId(proteinGroups.getProteinGroupId());
        entry.setName(proteinGroups.getProteinName());
        entry.setDescription(proteinGroups.getProteinName());

        addPrimaryProtein(proteinGroups, fields);

        //FieldAndRef fieldAndRef = new FieldAndRef();

        // addProteinInformation(fieldAndRef, proteinGroups, fields, refs, relSpecies);
        addProteinInformation(proteinGroups, fields, refs, relSpecies);
        additionalFields.setField(fields);
        entry.setAdditionalFields(additionalFields);
        cr.setRef(refs);
        entry.setCrossReferences(cr);
        return entry;
    }
//ID to investigate - E99MXF  entry count : 3997
//""2019-08-15 06:04:52 [uk.ac.ebi.ep.xml.ProteinCentricBatchJob.main()] INFO  u.a.e.e.x.t.ProteinGroupsProcessor - Processor 16 current entry : E3CHQJ  entry count : 165840
    // Processor 16 current entry : 4.2.1.136  entry count : 4969

    @Transactional(readOnly = true)
    private void addProteinInformation(ProteinGroups proteinGroups, Set<Field> fields, Set<Ref> refs, Set<String> relSpecies) {
        try (Stream<ProteinXml> protein = proteinXmlRepository.streamProteinDataByProteinGroupId(proteinGroups.getProteinGroupId())) {
            protein.parallel().forEach(data -> processEntries(data, relSpecies, fields, refs));
            addRelatedSpeciesField(relSpecies, fields);
        }
        //addRelatedSpeciesField(relSpecies, fields);
    }

    @Transactional(readOnly = true)
    private void addProteinInformationHibernate(ProteinGroups proteinGroups, Set<Field> fields, Set<Ref> refs, Set<String> relSpecies) {

        String query = "SELECT * FROM protein_xml WHERE protein_group_id=" + "'" + proteinGroups.getProteinGroupId() + "'";
        try (Stream<ProteinXml> stream = entityManagerFactory.createEntityManager().createNativeQuery(query, ProteinXml.class)
                .setHint(QueryHints.HINT_FETCH_SIZE, 1)
                .unwrap(Query.class)
                .stream()) {

            stream.forEach(data -> processEntries(data, relSpecies, fields, refs));
            addRelatedSpeciesField(relSpecies, fields);
        }

//        try (Stream<ProteinXml> protein = proteinXmlRepository.streamProteinDataByProteinGroupId(proteinGroups.getProteinGroupId())) {
//            protein.parallel().forEach(data -> processEntries(data, relSpecies, fields, refs));
//            addRelatedSpeciesField(relSpecies, fields);
//        }
        //addRelatedSpeciesField(relSpecies, fields);
    }

    @Deprecated
    private void addProteinInformation(FieldAndRef fieldAndRef, ProteinGroups proteinGroups, Set<Field> fields, Set<Ref> refs, Set<String> relSpecies) {

//            Stream<ProteinXml> protein1 = proteinXmlRepository.streamProteinDataByProteinGroupId(proteinGroups.getProteinGroupId());
//     List<FieldAndRef> ff = protein1.parallel()
//                    .map(data -> processEntries(fieldAndRef,data, relSpecies, fields, refs)).collect(Collectors.toList());
//            
        try (Stream<ProteinXml> protein = proteinXmlRepository.streamProteinDataByProteinGroupId(proteinGroups.getProteinGroupId())) {
            //protein.parallel().forEach(data -> processEntries(data, relSpecies, fields, refs));
            List<FieldAndRef> ff = protein.parallel()
                    .map(data -> processEntries(fieldAndRef, data, relSpecies, fields, refs)).collect(Collectors.toList());
            addRelatedSpeciesField(relSpecies, fields);
        }
//        addRelatedSpeciesField(relSpecies, fields);
//        additionalFields.setField(fields);
//        entry.setAdditionalFields(additionalFields);
    }

    private void addPrimaryProtein(ProteinGroups proteinGroups, Set<Field> fields) {
        PrimaryProtein primaryProtein = proteinGroups.getPrimaryProtein();
        if (primaryProtein != null) {

            addPrimaryProteinField(primaryProtein, fields);
            addPrimaryImage(primaryProtein, fields);
            addEntryTypeFields(primaryProtein, fields);

            addPrimaryFunctionFields(primaryProtein, fields);

        }

    }

    private void processEntries(ProteinXml uniprotEntry, Set<String> relSpecies, Set<Field> fields, Set<Ref> refs) {
        addScientificNameFields(uniprotEntry.getScientificName(), fields);
        addCommonNameFields(uniprotEntry.getCommonName(), fields);
        addAccessionXrefs(uniprotEntry.getAccession(), refs);
        addRelatedSpecies(uniprotEntry, relSpecies);

        addReactantFieldsAndXrefs(uniprotEntry, fields, refs);

        addCompoundDataFieldsAndXrefs(uniprotEntry, fields, refs);

        addDiseaseFieldsAndXrefs(uniprotEntry, fields, refs);

        addPrimaryEntities(uniprotEntry, fields);

        addEcXrefs(uniprotEntry, refs);
        addTaxonomyFieldAndXrefs(uniprotEntry, fields, refs);

        addUniprotFamilyFieldsAndXrefs(uniprotEntry, fields, refs);
        addPathwayFieldsAndXrefs(uniprotEntry, fields, refs);
        addReactionFieldsAndXrefs(uniprotEntry, fields, refs);

    }

    private FieldAndRef processEntries(FieldAndRef fieldAndRef, ProteinXml uniprotEntry, Set<String> relSpecies, Set<Field> fields, Set<Ref> refs) {
        addScientificNameFields(uniprotEntry.getScientificName(), fields);
        addCommonNameFields(uniprotEntry.getCommonName(), fields);
        addAccessionXrefs(uniprotEntry.getAccession(), refs);
        addRelatedSpecies(uniprotEntry, relSpecies);

        addReactantFieldsAndXrefs(uniprotEntry, fields, refs);

        addCompoundDataFieldsAndXrefs(uniprotEntry, fields, refs);

        addDiseaseFieldsAndXrefs(uniprotEntry, fields, refs);

        addPrimaryEntities(uniprotEntry, fields);

        addEcXrefs(uniprotEntry, refs);
        addTaxonomyFieldAndXrefs(uniprotEntry, fields, refs);

        addUniprotFamilyFieldsAndXrefs(uniprotEntry, fields, refs);
        addPathwayFieldsAndXrefs(uniprotEntry, fields, refs);
        addReactionFieldsAndXrefs(uniprotEntry, fields, refs);

        fieldAndRef.setField(fields);
        fieldAndRef.setRefs(refs);

//        additionalFields.setField(fields);
//        cr.setRef(refs);
//        entry.setAdditionalFields(additionalFields);
//        entry.setCrossReferences(cr);
        return fieldAndRef;

    }

    private void addPrimaryEntities(ProteinXml entry, Set<Field> fields) {
        if (entry.getAccession().equals(entry.getPrimaryAccession())) {

            addPrimarySynonymFields(entry, fields);
            addPrimaryEc(entry, fields);
            addPrimaryCatalyticActivityFields(entry, fields);
            addPrimaryGeneNameFields(entry, fields);
            addEnzymeFamilyToProteinField(entry, fields);
        }
    }

    private void addPrimarySynonymFields(ProteinXml entry, Set<Field> fields) {

        if (Objects.nonNull(entry.getSynonymNames()) && Objects.nonNull(entry.getProteinName())) {

            Optional<String> synonymName = Optional.ofNullable(entry.getSynonymNames());
            computeSynonymsAndBuildFields(synonymName.orElse(""), entry.getProteinName(), fields);

        }

    }

    private void addPrimaryEc(ProteinXml entry, Set<Field> fields) {
        addField(FieldName.EC.getName(), entry.getEcNumber(), fields);

    }

    private void addEcXrefs(ProteinXml entry, Set<Ref> refs) {

        Ref xref = new Ref(entry.getEcNumber(), DatabaseName.INTENZ.getDbName());
        refs.add(xref);

    }

    private void addEnzymeFamilyToProteinField(ProteinXml entry, Set<Field> fields) {
        addField(FieldName.ENZYME_FAMILY.getName(), computeEcToFamilyName(entry.getEcFamily()), fields);

    }

    private void addPrimaryGeneNameFields(ProteinXml entry, Set<Field> fields) {
        addGeneNameFields(entry, fields);

    }

    private void addPrimaryCatalyticActivityFields(ProteinXml entry, Set<Field> fields) {

        addField(FieldName.CATALYTIC_ACTIVITY.getName(), entry.getCatalyticActivity(), fields);
    }

    private void addRelatedSpeciesField(Set<String> relSpecies, Set<Field> fields) {
        if (!relSpecies.isEmpty()) {
            String rsField = relSpecies
                    .stream()
                    .collect(Collectors.joining("|"));
            Field field = new Field(FieldName.RELATED_SPECIES.getName(), rsField);
            fields.add(field);
        }
    }

    private void addRelatedSpecies(ProteinXml uniprotEntry, Set<String> relSpecies) {
        if (Objects.equals(uniprotEntry.getRelatedProteinsId(), uniprotEntry.getPrimaryRelatedProteinsId())) {

            //String species = uniprotEntry.getAccession() + ";" + uniprotEntry.getCommonName() + ";" + uniprotEntry.getScientificName() + ";" + uniprotEntry.getExpEvidenceFlag() + ";" + uniprotEntry.getTaxId();
            relSpecies.add(uniprotEntry.getAccession() + ";" + uniprotEntry.getCommonName() + ";" + uniprotEntry.getScientificName() + ";" + uniprotEntry.getExpEvidenceFlag() + ";" + uniprotEntry.getTaxId());

        }
    }

}
