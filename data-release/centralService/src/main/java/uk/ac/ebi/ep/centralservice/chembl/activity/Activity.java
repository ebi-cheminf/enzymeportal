
package uk.ac.ebi.ep.centralservice.chembl.activity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "activity_comment",
    "activity_id",
    "assay_chembl_id",
    "assay_description",
    "assay_type",
    "bao_endpoint",
    "bao_format",
    "bao_label",
    "canonical_smiles",
    "data_validity_comment",
    "data_validity_description",
    "document_chembl_id",
    "document_journal",
    "document_year",
    "ligand_efficiency",
    "molecule_chembl_id",
    "molecule_pref_name",
    "parent_molecule_chembl_id",
    "pchembl_value",
    "potential_duplicate",
    "published_relation",
    "published_type",
    "published_units",
    "published_value",
    "qudt_units",
    "record_id",
    "relation",
    "src_id",
    "standard_flag",
    "standard_relation",
    "standard_text_value",
    "standard_type",
    "standard_units",
    "standard_upper_value",
    "standard_value",
    "target_chembl_id",
    "target_organism",
    "target_pref_name",
    "target_tax_id",
    "text_value",
    "toid",
    "type",
    "units",
    "uo_units",
    "upper_value",
    "value"
})

/**
 *
 * @author joseph
 */
public class Activity {

    @JsonProperty("activity_comment")
    private Object activityComment;
    @JsonProperty("activity_id")
    private Integer activityId;
    @JsonProperty("assay_chembl_id")
    private String assayChemblId;
    @JsonProperty("assay_description")
    private String assayDescription;
    @JsonProperty("assay_type")
    private String assayType;
    @JsonProperty("bao_endpoint")
    private String baoEndpoint;
    @JsonProperty("bao_format")
    private String baoFormat;
    @JsonProperty("bao_label")
    private String baoLabel;
    @JsonProperty("canonical_smiles")
    private String canonicalSmiles;
    @JsonProperty("data_validity_comment")
    private Object dataValidityComment;
    @JsonProperty("data_validity_description")
    private Object dataValidityDescription;
    @JsonProperty("document_chembl_id")
    private String documentChemblId;
    @JsonProperty("document_journal")
    private String documentJournal;
    @JsonProperty("document_year")
    private Integer documentYear;
    @JsonProperty("ligand_efficiency")
    private Object ligandEfficiency;
    @JsonProperty("molecule_chembl_id")
    private String moleculeChemblId;
    @JsonProperty("molecule_pref_name")
    private String moleculePrefName;
    @JsonProperty("parent_molecule_chembl_id")
    private String parentMoleculeChemblId;
    @JsonProperty("pchembl_value")
    private Object pchemblValue;
    @JsonProperty("potential_duplicate")
    private Boolean potentialDuplicate;
    @JsonProperty("published_relation")
    private String publishedRelation;
    @JsonProperty("published_type")
    private String publishedType;
    @JsonProperty("published_units")
    private String publishedUnits;
    @JsonProperty("published_value")
    private String publishedValue;
    @JsonProperty("qudt_units")
    private String qudtUnits;
    @JsonProperty("record_id")
    private Integer recordId;
    @JsonProperty("relation")
    private String relation;
    @JsonProperty("src_id")
    private Integer srcId;
    @JsonProperty("standard_flag")
    private Boolean standardFlag;
    @JsonProperty("standard_relation")
    private String standardRelation;
    @JsonProperty("standard_text_value")
    private Object standardTextValue;
    @JsonProperty("standard_type")
    private String standardType;
    @JsonProperty("standard_units")
    private String standardUnits;
    @JsonProperty("standard_upper_value")
    private Object standardUpperValue;
    @JsonProperty("standard_value")
    private String standardValue;
    @JsonProperty("target_chembl_id")
    private String targetChemblId;
    @JsonProperty("target_organism")
    private String targetOrganism;
    @JsonProperty("target_pref_name")
    private String targetPrefName;
    @JsonProperty("target_tax_id")
    private String targetTaxId;
    @JsonProperty("text_value")
    private Object textValue;
    @JsonProperty("toid")
    private Object toid;
    @JsonProperty("type")
    private String type;
    @JsonProperty("units")
    private String units;
    @JsonProperty("uo_units")
    private String uoUnits;
    @JsonProperty("upper_value")
    private Object upperValue;
    @JsonProperty("value")
    private String value;

    @JsonProperty("activity_comment")
    public Object getActivityComment() {
        return activityComment;
    }

    @JsonProperty("activity_comment")
    public void setActivityComment(Object activityComment) {
        this.activityComment = activityComment;
    }

    @JsonProperty("activity_id")
    public Integer getActivityId() {
        return activityId;
    }

    @JsonProperty("activity_id")
    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    @JsonProperty("assay_chembl_id")
    public String getAssayChemblId() {
        return assayChemblId;
    }

    @JsonProperty("assay_chembl_id")
    public void setAssayChemblId(String assayChemblId) {
        this.assayChemblId = assayChemblId;
    }

    @JsonProperty("assay_description")
    public String getAssayDescription() {
        return assayDescription;
    }

    @JsonProperty("assay_description")
    public void setAssayDescription(String assayDescription) {
        this.assayDescription = assayDescription;
    }

    @JsonProperty("assay_type")
    public String getAssayType() {
        return assayType;
    }

    @JsonProperty("assay_type")
    public void setAssayType(String assayType) {
        this.assayType = assayType;
    }

    @JsonProperty("bao_endpoint")
    public String getBaoEndpoint() {
        return baoEndpoint;
    }

    @JsonProperty("bao_endpoint")
    public void setBaoEndpoint(String baoEndpoint) {
        this.baoEndpoint = baoEndpoint;
    }

    @JsonProperty("bao_format")
    public String getBaoFormat() {
        return baoFormat;
    }

    @JsonProperty("bao_format")
    public void setBaoFormat(String baoFormat) {
        this.baoFormat = baoFormat;
    }

    @JsonProperty("bao_label")
    public String getBaoLabel() {
        return baoLabel;
    }

    @JsonProperty("bao_label")
    public void setBaoLabel(String baoLabel) {
        this.baoLabel = baoLabel;
    }

    @JsonProperty("canonical_smiles")
    public String getCanonicalSmiles() {
        return canonicalSmiles;
    }

    @JsonProperty("canonical_smiles")
    public void setCanonicalSmiles(String canonicalSmiles) {
        this.canonicalSmiles = canonicalSmiles;
    }

    @JsonProperty("data_validity_comment")
    public Object getDataValidityComment() {
        return dataValidityComment;
    }

    @JsonProperty("data_validity_comment")
    public void setDataValidityComment(Object dataValidityComment) {
        this.dataValidityComment = dataValidityComment;
    }

    @JsonProperty("data_validity_description")
    public Object getDataValidityDescription() {
        return dataValidityDescription;
    }

    @JsonProperty("data_validity_description")
    public void setDataValidityDescription(Object dataValidityDescription) {
        this.dataValidityDescription = dataValidityDescription;
    }

    @JsonProperty("document_chembl_id")
    public String getDocumentChemblId() {
        return documentChemblId;
    }

    @JsonProperty("document_chembl_id")
    public void setDocumentChemblId(String documentChemblId) {
        this.documentChemblId = documentChemblId;
    }

    @JsonProperty("document_journal")
    public String getDocumentJournal() {
        return documentJournal;
    }

    @JsonProperty("document_journal")
    public void setDocumentJournal(String documentJournal) {
        this.documentJournal = documentJournal;
    }

    @JsonProperty("document_year")
    public Integer getDocumentYear() {
        return documentYear;
    }

    @JsonProperty("document_year")
    public void setDocumentYear(Integer documentYear) {
        this.documentYear = documentYear;
    }

    @JsonProperty("ligand_efficiency")
    public Object getLigandEfficiency() {
        return ligandEfficiency;
    }

    @JsonProperty("ligand_efficiency")
    public void setLigandEfficiency(Object ligandEfficiency) {
        this.ligandEfficiency = ligandEfficiency;
    }

    @JsonProperty("molecule_chembl_id")
    public String getMoleculeChemblId() {
        return moleculeChemblId;
    }

    @JsonProperty("molecule_chembl_id")
    public void setMoleculeChemblId(String moleculeChemblId) {
        this.moleculeChemblId = moleculeChemblId;
    }

    @JsonProperty("molecule_pref_name")
    public String getMoleculePrefName() {
        if(moleculePrefName == null){
            moleculePrefName = moleculeChemblId;
        }
        return moleculePrefName;
    }

    @JsonProperty("molecule_pref_name")
    public void setMoleculePrefName(String moleculePrefName) {
        this.moleculePrefName = moleculePrefName;
    }

    @JsonProperty("parent_molecule_chembl_id")
    public String getParentMoleculeChemblId() {
        return parentMoleculeChemblId;
    }

    @JsonProperty("parent_molecule_chembl_id")
    public void setParentMoleculeChemblId(String parentMoleculeChemblId) {
        this.parentMoleculeChemblId = parentMoleculeChemblId;
    }

    @JsonProperty("pchembl_value")
    public Object getPchemblValue() {
        return pchemblValue;
    }

    @JsonProperty("pchembl_value")
    public void setPchemblValue(Object pchemblValue) {
        this.pchemblValue = pchemblValue;
    }

    @JsonProperty("potential_duplicate")
    public Boolean getPotentialDuplicate() {
        return potentialDuplicate;
    }

    @JsonProperty("potential_duplicate")
    public void setPotentialDuplicate(Boolean potentialDuplicate) {
        this.potentialDuplicate = potentialDuplicate;
    }

    @JsonProperty("published_relation")
    public String getPublishedRelation() {
        return publishedRelation;
    }

    @JsonProperty("published_relation")
    public void setPublishedRelation(String publishedRelation) {
        this.publishedRelation = publishedRelation;
    }

    @JsonProperty("published_type")
    public String getPublishedType() {
        return publishedType;
    }

    @JsonProperty("published_type")
    public void setPublishedType(String publishedType) {
        this.publishedType = publishedType;
    }

    @JsonProperty("published_units")
    public String getPublishedUnits() {
        return publishedUnits;
    }

    @JsonProperty("published_units")
    public void setPublishedUnits(String publishedUnits) {
        this.publishedUnits = publishedUnits;
    }

    @JsonProperty("published_value")
    public String getPublishedValue() {
        return publishedValue;
    }

    @JsonProperty("published_value")
    public void setPublishedValue(String publishedValue) {
        this.publishedValue = publishedValue;
    }

    @JsonProperty("qudt_units")
    public String getQudtUnits() {
        return qudtUnits;
    }

    @JsonProperty("qudt_units")
    public void setQudtUnits(String qudtUnits) {
        this.qudtUnits = qudtUnits;
    }

    @JsonProperty("record_id")
    public Integer getRecordId() {
        return recordId;
    }

    @JsonProperty("record_id")
    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    @JsonProperty("relation")
    public String getRelation() {
        return relation;
    }

    @JsonProperty("relation")
    public void setRelation(String relation) {
        this.relation = relation;
    }

    @JsonProperty("src_id")
    public Integer getSrcId() {
        return srcId;
    }

    @JsonProperty("src_id")
    public void setSrcId(Integer srcId) {
        this.srcId = srcId;
    }

    @JsonProperty("standard_flag")
    public Boolean getStandardFlag() {
        return standardFlag;
    }

    @JsonProperty("standard_flag")
    public void setStandardFlag(Boolean standardFlag) {
        this.standardFlag = standardFlag;
    }

    @JsonProperty("standard_relation")
    public String getStandardRelation() {
        return standardRelation;
    }

    @JsonProperty("standard_relation")
    public void setStandardRelation(String standardRelation) {
        this.standardRelation = standardRelation;
    }

    @JsonProperty("standard_text_value")
    public Object getStandardTextValue() {
        return standardTextValue;
    }

    @JsonProperty("standard_text_value")
    public void setStandardTextValue(Object standardTextValue) {
        this.standardTextValue = standardTextValue;
    }

    @JsonProperty("standard_type")
    public String getStandardType() {
        return standardType;
    }

    @JsonProperty("standard_type")
    public void setStandardType(String standardType) {
        this.standardType = standardType;
    }

    @JsonProperty("standard_units")
    public String getStandardUnits() {
        return standardUnits;
    }

    @JsonProperty("standard_units")
    public void setStandardUnits(String standardUnits) {
        this.standardUnits = standardUnits;
    }

    @JsonProperty("standard_upper_value")
    public Object getStandardUpperValue() {
        return standardUpperValue;
    }

    @JsonProperty("standard_upper_value")
    public void setStandardUpperValue(Object standardUpperValue) {
        this.standardUpperValue = standardUpperValue;
    }

    @JsonProperty("standard_value")
    public String getStandardValue() {
        if (standardValue == null || "".equals(standardValue) || "null".equals(standardValue)) {
            if (standardType.equalsIgnoreCase("IC50")) {
                standardValue = "2000";
            } else if (standardType.equalsIgnoreCase("Inhibition")) {
                standardValue = "0";
            }else{
                standardValue="0";
            }
        }

        return standardValue;
    }

    @JsonProperty("standard_value")
    public void setStandardValue(String standardValue) {
        this.standardValue = standardValue;
    }

    @JsonProperty("target_chembl_id")
    public String getTargetChemblId() {
        return targetChemblId;
    }

    @JsonProperty("target_chembl_id")
    public void setTargetChemblId(String targetChemblId) {
        this.targetChemblId = targetChemblId;
    }

    @JsonProperty("target_organism")
    public String getTargetOrganism() {
        return targetOrganism;
    }

    @JsonProperty("target_organism")
    public void setTargetOrganism(String targetOrganism) {
        this.targetOrganism = targetOrganism;
    }

    @JsonProperty("target_pref_name")
    public String getTargetPrefName() {
        return targetPrefName;
    }

    @JsonProperty("target_pref_name")
    public void setTargetPrefName(String targetPrefName) {
        this.targetPrefName = targetPrefName;
    }

    @JsonProperty("target_tax_id")
    public String getTargetTaxId() {
        return targetTaxId;
    }

    @JsonProperty("target_tax_id")
    public void setTargetTaxId(String targetTaxId) {
        this.targetTaxId = targetTaxId;
    }

    @JsonProperty("text_value")
    public Object getTextValue() {
        return textValue;
    }

    @JsonProperty("text_value")
    public void setTextValue(Object textValue) {
        this.textValue = textValue;
    }

    @JsonProperty("toid")
    public Object getToid() {
        return toid;
    }

    @JsonProperty("toid")
    public void setToid(Object toid) {
        this.toid = toid;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("units")
    public String getUnits() {
        return units;
    }

    @JsonProperty("units")
    public void setUnits(String units) {
        this.units = units;
    }

    @JsonProperty("uo_units")
    public String getUoUnits() {
        return uoUnits;
    }

    @JsonProperty("uo_units")
    public void setUoUnits(String uoUnits) {
        this.uoUnits = uoUnits;
    }

    @JsonProperty("upper_value")
    public Object getUpperValue() {
        return upperValue;
    }

    @JsonProperty("upper_value")
    public void setUpperValue(Object upperValue) {
        this.upperValue = upperValue;
    }

    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.moleculeChemblId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Activity other = (Activity) obj;
        if (!Objects.equals(this.moleculeChemblId, other.moleculeChemblId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Activity{" + "moleculeChemblId=" + moleculeChemblId + ", moleculePrefName=" + moleculePrefName + ", standardType=" + standardType + ", standardUnits=" + standardUnits + ", standardValue=" + standardValue + ", targetChemblId=" + targetChemblId + '}';
    }

//    @JsonProperty("activity_comment")
//    private Object activityComment;
//    @JsonProperty("activity_id")
//    private Integer activityId;
//    @JsonProperty("assay_chembl_id")
//    private String assayChemblId;
//    @JsonProperty("assay_description")
//    private String assayDescription;
//    @JsonProperty("assay_type")
//    private String assayType;
//    @JsonProperty("bao_endpoint")
//    private String baoEndpoint;
//    @JsonProperty("bao_format")
//    private String baoFormat;
//    @JsonProperty("canonical_smiles")
//    private String canonicalSmiles;
//    @JsonProperty("data_validity_comment")
//    private Object dataValidityComment;
//    @JsonProperty("document_chembl_id")
//    private String documentChemblId;
//    @JsonProperty("document_journal")
//    private String documentJournal;
//    @JsonProperty("document_year")
//    private Integer documentYear;
//    @JsonProperty("molecule_chembl_id")
//    private String moleculeChemblId;
//    @JsonProperty("pchembl_value")
//    private String pchemblValue;
//    @JsonProperty("potential_duplicate")
//    private Object potentialDuplicate;
//    @JsonProperty("published_relation")
//    private String publishedRelation;
//    @JsonProperty("published_type")
//    private String publishedType;
//    @JsonProperty("published_units")
//    private String publishedUnits;
//    @JsonProperty("published_value")
//    private String publishedValue;
//    @JsonProperty("qudt_units")
//    private String qudtUnits;
//    @JsonProperty("record_id")
//    private Integer recordId;
//    @JsonProperty("standard_flag")
//    private Boolean standardFlag;
//    @JsonProperty("standard_relation")
//    private String standardRelation;
//    @JsonProperty("standard_type")
//    private String standardType;
//    @JsonProperty("standard_units")
//    private String standardUnits;
//    @JsonProperty("standard_value")
//    private String standardValue;
//    @JsonProperty("target_chembl_id")
//    private String targetChemblId;
//    @JsonProperty("target_organism")
//    private String targetOrganism;
//    @JsonProperty("target_pref_name")
//    private String targetPrefName;
//    @JsonProperty("uo_units")
//    private String uoUnits;
//    @JsonIgnore
//    private final Map<String, Object> additionalProperties = new HashMap<>();
//
//    /**
//     *
//     * @return The activityComment
//     */
//    @JsonProperty("activity_comment")
//    public Object getActivityComment() {
//        return activityComment;
//    }
//
//    /**
//     *
//     * @param activityComment The activity_comment
//     */
//    @JsonProperty("activity_comment")
//    public void setActivityComment(Object activityComment) {
//        this.activityComment = activityComment;
//    }
//
//    /**
//     *
//     * @return The activityId
//     */
//    @JsonProperty("activity_id")
//    public Integer getActivityId() {
//        return activityId;
//    }
//
//    /**
//     *
//     * @param activityId The activity_id
//     */
//    @JsonProperty("activity_id")
//    public void setActivityId(Integer activityId) {
//        this.activityId = activityId;
//    }
//
//    /**
//     *
//     * @return The assayChemblId
//     */
//    @JsonProperty("assay_chembl_id")
//    public String getAssayChemblId() {
//        return assayChemblId;
//    }
//
//    /**
//     *
//     * @param assayChemblId The assay_chembl_id
//     */
//    @JsonProperty("assay_chembl_id")
//    public void setAssayChemblId(String assayChemblId) {
//        this.assayChemblId = assayChemblId;
//    }
//
//    /**
//     *
//     * @return The assayDescription
//     */
//    @JsonProperty("assay_description")
//    public String getAssayDescription() {
//        return assayDescription;
//    }
//
//    /**
//     *
//     * @param assayDescription The assay_description
//     */
//    @JsonProperty("assay_description")
//    public void setAssayDescription(String assayDescription) {
//        this.assayDescription = assayDescription;
//    }
//
//    /**
//     *
//     * @return The assayType
//     */
//    @JsonProperty("assay_type")
//    public String getAssayType() {
//        return assayType;
//    }
//
//    /**
//     *
//     * @param assayType The assay_type
//     */
//    @JsonProperty("assay_type")
//    public void setAssayType(String assayType) {
//        this.assayType = assayType;
//    }
//
//    /**
//     *
//     * @return The baoEndpoint
//     */
//    @JsonProperty("bao_endpoint")
//    public String getBaoEndpoint() {
//        return baoEndpoint;
//    }
//
//    /**
//     *
//     * @param baoEndpoint The bao_endpoint
//     */
//    @JsonProperty("bao_endpoint")
//    public void setBaoEndpoint(String baoEndpoint) {
//        this.baoEndpoint = baoEndpoint;
//    }
//
//    /**
//     *
//     * @return The baoFormat
//     */
//    @JsonProperty("bao_format")
//    public String getBaoFormat() {
//        return baoFormat;
//    }
//
//    /**
//     *
//     * @param baoFormat The bao_format
//     */
//    @JsonProperty("bao_format")
//    public void setBaoFormat(String baoFormat) {
//        this.baoFormat = baoFormat;
//    }
//
//    /**
//     *
//     * @return The canonicalSmiles
//     */
//    @JsonProperty("canonical_smiles")
//    public String getCanonicalSmiles() {
//        return canonicalSmiles;
//    }
//
//    /**
//     *
//     * @param canonicalSmiles The canonical_smiles
//     */
//    @JsonProperty("canonical_smiles")
//    public void setCanonicalSmiles(String canonicalSmiles) {
//        this.canonicalSmiles = canonicalSmiles;
//    }
//
//    /**
//     *
//     * @return The dataValidityComment
//     */
//    @JsonProperty("data_validity_comment")
//    public Object getDataValidityComment() {
//        return dataValidityComment;
//    }
//
//    /**
//     *
//     * @param dataValidityComment The data_validity_comment
//     */
//    @JsonProperty("data_validity_comment")
//    public void setDataValidityComment(Object dataValidityComment) {
//        this.dataValidityComment = dataValidityComment;
//    }
//
//    /**
//     *
//     * @return The documentChemblId
//     */
//    @JsonProperty("document_chembl_id")
//    public String getDocumentChemblId() {
//        return documentChemblId;
//    }
//
//    /**
//     *
//     * @param documentChemblId The document_chembl_id
//     */
//    @JsonProperty("document_chembl_id")
//    public void setDocumentChemblId(String documentChemblId) {
//        this.documentChemblId = documentChemblId;
//    }
//
//    /**
//     *
//     * @return The documentJournal
//     */
//    @JsonProperty("document_journal")
//    public String getDocumentJournal() {
//        return documentJournal;
//    }
//
//    /**
//     *
//     * @param documentJournal The document_journal
//     */
//    @JsonProperty("document_journal")
//    public void setDocumentJournal(String documentJournal) {
//        this.documentJournal = documentJournal;
//    }
//
//    /**
//     *
//     * @return The documentYear
//     */
//    @JsonProperty("document_year")
//    public Integer getDocumentYear() {
//        return documentYear;
//    }
//
//    /**
//     *
//     * @param documentYear The document_year
//     */
//    @JsonProperty("document_year")
//    public void setDocumentYear(Integer documentYear) {
//        this.documentYear = documentYear;
//    }
//
//    /**
//     *
//     * @return The moleculeChemblId
//     */
//    @JsonProperty("molecule_chembl_id")
//    public String getMoleculeChemblId() {
//        return moleculeChemblId;
//    }
//
//    /**
//     *
//     * @param moleculeChemblId The molecule_chembl_id
//     */
//    @JsonProperty("molecule_chembl_id")
//    public void setMoleculeChemblId(String moleculeChemblId) {
//        this.moleculeChemblId = moleculeChemblId;
//    }
//
//    /**
//     *
//     * @return The pchemblValue
//     */
//    @JsonProperty("pchembl_value")
//    public String getPchemblValue() {
//        return pchemblValue;
//    }
//
//    /**
//     *
//     * @param pchemblValue The pchembl_value
//     */
//    @JsonProperty("pchembl_value")
//    public void setPchemblValue(String pchemblValue) {
//        this.pchemblValue = pchemblValue;
//    }
//
//    /**
//     *
//     * @return The potentialDuplicate
//     */
//    @JsonProperty("potential_duplicate")
//    public Object getPotentialDuplicate() {
//        return potentialDuplicate;
//    }
//
//    /**
//     *
//     * @param potentialDuplicate The potential_duplicate
//     */
//    @JsonProperty("potential_duplicate")
//    public void setPotentialDuplicate(Object potentialDuplicate) {
//        this.potentialDuplicate = potentialDuplicate;
//    }
//
//    /**
//     *
//     * @return The publishedRelation
//     */
//    @JsonProperty("published_relation")
//    public String getPublishedRelation() {
//        return publishedRelation;
//    }
//
//    /**
//     *
//     * @param publishedRelation The published_relation
//     */
//    @JsonProperty("published_relation")
//    public void setPublishedRelation(String publishedRelation) {
//        this.publishedRelation = publishedRelation;
//    }
//
//    /**
//     *
//     * @return The publishedType
//     */
//    @JsonProperty("published_type")
//    public String getPublishedType() {
//        return publishedType;
//    }
//
//    /**
//     *
//     * @param publishedType The published_type
//     */
//    @JsonProperty("published_type")
//    public void setPublishedType(String publishedType) {
//        this.publishedType = publishedType;
//    }
//
//    /**
//     *
//     * @return The publishedUnits
//     */
//    @JsonProperty("published_units")
//    public String getPublishedUnits() {
//        return publishedUnits;
//    }
//
//    /**
//     *
//     * @param publishedUnits The published_units
//     */
//    @JsonProperty("published_units")
//    public void setPublishedUnits(String publishedUnits) {
//        this.publishedUnits = publishedUnits;
//    }
//
//    /**
//     *
//     * @return The publishedValue
//     */
//    @JsonProperty("published_value")
//    public String getPublishedValue() {
//        return publishedValue;
//    }
//
//    /**
//     *
//     * @param publishedValue The published_value
//     */
//    @JsonProperty("published_value")
//    public void setPublishedValue(String publishedValue) {
//        this.publishedValue = publishedValue;
//    }
//
//    /**
//     *
//     * @return The qudtUnits
//     */
//    @JsonProperty("qudt_units")
//    public String getQudtUnits() {
//        return qudtUnits;
//    }
//
//    /**
//     *
//     * @param qudtUnits The qudt_units
//     */
//    @JsonProperty("qudt_units")
//    public void setQudtUnits(String qudtUnits) {
//        this.qudtUnits = qudtUnits;
//    }
//
//    /**
//     *
//     * @return The recordId
//     */
//    @JsonProperty("record_id")
//    public Integer getRecordId() {
//        return recordId;
//    }
//
//    /**
//     *
//     * @param recordId The record_id
//     */
//    @JsonProperty("record_id")
//    public void setRecordId(Integer recordId) {
//        this.recordId = recordId;
//    }
//
//    /**
//     *
//     * @return The standardFlag
//     */
//    @JsonProperty("standard_flag")
//    public Boolean getStandardFlag() {
//        return standardFlag;
//    }
//
//    /**
//     *
//     * @param standardFlag The standard_flag
//     */
//    @JsonProperty("standard_flag")
//    public void setStandardFlag(Boolean standardFlag) {
//        this.standardFlag = standardFlag;
//    }
//
//    /**
//     *
//     * @return The standardRelation
//     */
//    @JsonProperty("standard_relation")
//    public String getStandardRelation() {
//        return standardRelation;
//    }
//
//    /**
//     *
//     * @param standardRelation The standard_relation
//     */
//    @JsonProperty("standard_relation")
//    public void setStandardRelation(String standardRelation) {
//        this.standardRelation = standardRelation;
//    }
//
//    /**
//     *
//     * @return The standardType
//     */
//    @JsonProperty("standard_type")
//    public String getStandardType() {
//        return standardType;
//    }
//
//    /**
//     *
//     * @param standardType The standard_type
//     */
//    @JsonProperty("standard_type")
//    public void setStandardType(String standardType) {
//        this.standardType = standardType;
//    }
//
//    /**
//     *
//     * @return The standardUnits
//     */
//    @JsonProperty("standard_units")
//    public String getStandardUnits() {
//        return standardUnits;
//    }
//
//    /**
//     *
//     * @param standardUnits The standard_units
//     */
//    @JsonProperty("standard_units")
//    public void setStandardUnits(String standardUnits) {
//        this.standardUnits = standardUnits;
//    }
//
//    /**
//     *
//     * @return The standardValue
//     */
//    @JsonProperty("standard_value")
//    public String getStandardValue() {
//        return standardValue;
//    }
//
//    /**
//     *
//     * @param standardValue The standard_value
//     */
//    @JsonProperty("standard_value")
//    public void setStandardValue(String standardValue) {
//        this.standardValue = standardValue;
//    }
//
//    /**
//     *
//     * @return The targetChemblId
//     */
//    @JsonProperty("target_chembl_id")
//    public String getTargetChemblId() {
//        return targetChemblId;
//    }
//
//    /**
//     *
//     * @param targetChemblId The target_chembl_id
//     */
//    @JsonProperty("target_chembl_id")
//    public void setTargetChemblId(String targetChemblId) {
//        this.targetChemblId = targetChemblId;
//    }
//
//    /**
//     *
//     * @return The targetOrganism
//     */
//    @JsonProperty("target_organism")
//    public String getTargetOrganism() {
//        return targetOrganism;
//    }
//
//    /**
//     *
//     * @param targetOrganism The target_organism
//     */
//    @JsonProperty("target_organism")
//    public void setTargetOrganism(String targetOrganism) {
//        this.targetOrganism = targetOrganism;
//    }
//
//    /**
//     *
//     * @return The targetPrefName
//     */
//    @JsonProperty("target_pref_name")
//    public String getTargetPrefName() {
//        return targetPrefName;
//    }
//
//    /**
//     *
//     * @param targetPrefName The target_pref_name
//     */
//    @JsonProperty("target_pref_name")
//    public void setTargetPrefName(String targetPrefName) {
//        this.targetPrefName = targetPrefName;
//    }
//
//    /**
//     *
//     * @return The uoUnits
//     */
//    @JsonProperty("uo_units")
//    public String getUoUnits() {
//        return uoUnits;
//    }
//
//    /**
//     *
//     * @param uoUnits The uo_units
//     */
//    @JsonProperty("uo_units")
//    public void setUoUnits(String uoUnits) {
//        this.uoUnits = uoUnits;
//    }
//
//    @JsonAnyGetter
//    public Map<String, Object> getAdditionalProperties() {
//        return this.additionalProperties;
//    }
//
//    @JsonAnySetter
//    public void setAdditionalProperty(String name, Object value) {
//        this.additionalProperties.put(name, value);
//    }
//
//    @Override
//    public int hashCode() {
//        int hash = 7;
//        hash = 37 * hash + Objects.hashCode(this.moleculeChemblId);
//        return hash;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (obj == null) {
//            return false;
//        }
//        if (getClass() != obj.getClass()) {
//            return false;
//        }
//        final Activity other = (Activity) obj;
//        if (!Objects.equals(this.moleculeChemblId, other.moleculeChemblId)) {
//            return false;
//        }
//        return true;
//    }
}
