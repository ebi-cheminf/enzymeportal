package uk.ac.ebi.ep.xml.transformer;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.util.StringUtils;
import uk.ac.ebi.ep.xml.entities.EnzymePortalUniqueEc;
import uk.ac.ebi.ep.xml.entities.IntenzAltNames;
import uk.ac.ebi.ep.xml.entities.Protein;
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
 * @author <a href="mailto:joseph@ebi.ac.uk">Joseph</a>
 */
@Slf4j
public class EnzymeProcessor extends XmlTransformer implements ItemProcessor<EnzymePortalUniqueEc, Entry> {

    private final AtomicInteger count = new AtomicInteger(1);
    private final ProteinXmlRepository proteinXmlRepository;

    public EnzymeProcessor(ProteinXmlRepository proteinXmlRepository) {
        this.proteinXmlRepository = proteinXmlRepository;
    }

    @Override
    public Entry process(EnzymePortalUniqueEc enzyme) throws Exception {

        Set<Field> fields = new HashSet<>();
        Set<Ref> refs = new HashSet<>();
        AdditionalFields additionalFields = new AdditionalFields();
        CrossReferences cr = new CrossReferences();

        if (log.isDebugEnabled()) {
            log.debug("Processor " + Runtime.getRuntime().availableProcessors() + " current entry : " + enzyme.getEcNumber() + "  entry count : " + count.getAndIncrement());
        }
        Entry entry = new Entry();
        entry.setId(enzyme.getEcNumber());
        entry.setName(enzyme.getEnzymeName());

        entry.setDescription(String.format("%s %s", enzyme.getEcNumber(), enzyme.getEnzymeName()));

        addEnzymeFamilyField(enzyme.getEcNumber(), fields);

        addCofactorsField(enzyme.getCofactor(), fields);
        addCatalyticActivityField(enzyme.getCatalyticActivity(), fields);
        addAltNamesField(enzyme.getIntenzAltNamesSet(), fields);
        addEcSource(enzyme.getEcNumber(), refs);

        addProteinInformation(enzyme, fields, refs);

        additionalFields.setField(fields);
        entry.setAdditionalFields(additionalFields);

        cr.setRef(refs);
        entry.setCrossReferences(cr);

        return entry;
    }

    private void addProteinInformation(EnzymePortalUniqueEc enzyme, Set<Field> fields, Set<Ref> refs) {
        try (Stream<Protein> protein = proteinXmlRepository.streamProteinByEcNumber(enzyme.getEcNumber())) {
            protein.parallel().forEach(data -> processUniprotEntry(data, fields, refs));
        }
    }

    private void processUniprotEntry(Protein uniprotEntry, Set<Field> fields, Set<Ref> refs) {

        addProteinNameFields(uniprotEntry.getProteinName(), fields);

        addScientificNameFields(uniprotEntry.getScientificName(), fields);
        addCommonNameFields(uniprotEntry.getCommonName(), fields);
        addGeneNameFields(uniprotEntry, fields);

        addUniprotFamilyFieldsAndXrefs(uniprotEntry, fields, refs);

        addSynonymFields(uniprotEntry.getSynonymNames(), uniprotEntry.getProteinName(), fields);

        addAccessionXrefs(uniprotEntry.getAccession(), refs);
        addTaxonomyXrefs(uniprotEntry, refs);

        addCompoundFieldsAndXrefs(uniprotEntry, fields, refs);
        addDiseaseFieldsAndXrefs(uniprotEntry, fields, refs);
        addPathwaysXrefs(uniprotEntry, refs);
        addReactantFieldsAndXrefs(uniprotEntry, fields, refs);
        addReactionFieldsAndXrefs(uniprotEntry, fields, refs);
    }

    private void addAltNamesField(Set<IntenzAltNames> altNames, Set<Field> fields) {

        altNames
                .stream()
                .map(altName -> new Field(FieldName.INTENZ_ALT_NAMES.getName(), altName.getAltName()))
                .forEach(fields::add);

    }

    private void addCofactorsField(String cofactor, Set<Field> fields) {
        if (Objects.nonNull(cofactor)) {
            fields.add(new Field(FieldName.INTENZ_COFACTORS.getName(), cofactor));
        }
    }

    private void addCatalyticActivityField(String catalyticActivity, Set<Field> fields) {
        if (catalyticActivity != null) {
            fields.add(new Field(FieldName.CATALYTIC_ACTIVITY.getName(), catalyticActivity));
        }
    }

    private void addEnzymeFamilyField(String ec, Set<Field> fields) {
        if (Objects.nonNull(ec)) {
            fields.add(new Field(FieldName.ENZYME_FAMILY.getName(), computeFamily(ec)));
        }
    }

    private void addEcSource(String ec, Set<Ref> refs) {
        if (!StringUtils.isEmpty(ec)) {
            refs.add(new Ref(ec, DatabaseName.INTENZ.getDbName()));
        }
    }

    @Override
    void addUniprotFamilyFieldsAndXrefs(Protein family, Set<Field> fields, Set<Ref> refs) {

        if (Objects.nonNull(family.getFamilyGroupId()) && Objects.nonNull(family.getFamilyName())) {

            fields.add(new Field(FieldName.PROTEIN_FAMILY.getName(), family.getFamilyName()));
            fields.add(new Field(FieldName.PROTEIN_FAMILY_ID.getName(), family.getFamilyGroupId()));
            refs.add(new Ref(family.getFamilyGroupId(), DatabaseName.PROTEIN_FAMILY.getDbName()));
        }
    }

    @Override
    void addDiseaseFieldsAndXrefs(Protein disease, Set<Field> fields, Set<Ref> refs) {

        if (Objects.nonNull(disease.getOmimNumber()) && Objects.nonNull(disease.getDiseaseName())) {

            fields.add(new Field(FieldName.DISEASE_NAME.getName(), disease.getDiseaseName()));
            refs.add(new Ref(disease.getOmimNumber(), DatabaseName.OMIM.getDbName()));
        }

    }

    private void addTaxonomyXrefs(Protein taxonomy, Set<Ref> refs) {

        if (Objects.nonNull(taxonomy.getTaxId())) {

            refs.add(new Ref(Long.toString(taxonomy.getTaxId()), DatabaseName.TAXONOMY.getDbName()));

        }
    }

    private void addCompoundFieldsAndXrefs(Protein compound, Set<Field> fields, Set<Ref> refs) {

        if (Objects.nonNull(compound.getCompoundSource()) && Objects.nonNull(compound.getCompoundId()) && Objects.nonNull(compound.getCompoundName())) {

            fields.add(new Field(FieldName.COMPOUND_NAME.getName(), compound.getCompoundName()));
            refs.add(new Ref(compound.getCompoundId(), compound.getCompoundSource().toUpperCase()));
        }

    }

    private void addPathwaysXrefs(Protein pathway, Set<Ref> refs) {

        if (Objects.nonNull(pathway.getPathwayId())) {
            refs.add(new Ref(parseReactomePathwayId(pathway.getPathwayId()), DatabaseName.REACTOME.getDbName()));
        }
    }

}
