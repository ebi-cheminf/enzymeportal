package uk.ac.ebi.ep.xml.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import uk.ac.ebi.ep.xml.entity.enzyme.UniprotEntryEnzyme;

/**
 *
 * @author Joseph
 */
@Entity
@Table(name = "UNIPROT_FAMILIES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UniprotFamilies.findAll", query = "SELECT u FROM UniprotFamilies u")
    , @NamedQuery(name = "UniprotFamilies.findByDbentryId", query = "SELECT u FROM UniprotFamilies u WHERE u.dbentryId = :dbentryId")
    //, @NamedQuery(name = "UniprotFamilies.findByFamilyName", query = "SELECT u FROM UniprotFamilies u WHERE u.familyName = :familyName")
    , @NamedQuery(name = "UniprotFamilies.findByUniprotFamilyId", query = "SELECT u FROM UniprotFamilies u WHERE u.uniprotFamilyId = :uniprotFamilyId")})
public class UniprotFamilies implements Serializable {
//
//    @JoinColumn(name = "ACCESSION", referencedColumnName = "ACCESSION")
//    @ManyToOne
//    private UniprotEntryEnzyme accession;
////    @JoinColumn(name = "FAMILY_NAME_ID", referencedColumnName = "FAMILY_NAME_ID")
////    @ManyToOne
////    private UniqueFamilyName familyNameId;
//
//    private static final long serialVersionUID = 1L;
//    @Size(max = 15)
//    @Column(name = "DBENTRY_ID")
//    private String dbentryId;
//    @Size(max = 4000)
//    @Column(name = "FAMILY_NAME")
//    private String familyName;
//    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
//    @Id
//    @Basic(optional = false)
//    @NotNull
//    @Column(name = "UNIPROT_FAMILY_ID")
//    private Long uniprotFamilyId;

    private static final long serialVersionUID = 1L;
    @Size(max = 15)
    @Column(name = "DBENTRY_ID")
    private String dbentryId;
    @Size(max = 4000)
    @Column(name = "FAMILY_NAME")
    private String familyName;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "UNIPROT_FAMILY_ID")
    private Long uniprotFamilyId;
    @Size(max = 10)
    @Column(name = "FAMILY_GROUP_ID")
    private String familyGroupId;
    @JoinColumn(name = "ACCESSION", referencedColumnName = "ACCESSION")
    @ManyToOne
    private UniprotEntryEnzyme accession;
//    @JoinColumn(name = "FAMILY_NAME_ID", referencedColumnName = "FAMILY_NAME_ID")
//    @ManyToOne
//    private UniqueFamilyName familyNameId;

    public UniprotFamilies() {
    }

    public UniprotFamilies(Long uniprotFamilyId) {
        this.uniprotFamilyId = uniprotFamilyId;
    }

    public String getDbentryId() {
        return dbentryId;
    }

    public void setDbentryId(String dbentryId) {
        this.dbentryId = dbentryId;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public Long getUniprotFamilyId() {
        return uniprotFamilyId;
    }

    public void setUniprotFamilyId(Long uniprotFamilyId) {
        this.uniprotFamilyId = uniprotFamilyId;
    }

//    public UniprotEntry getAccession() {
//        return accession;
//    }
//
//    public void setAccession(UniprotEntry accession) {
//        this.accession = accession;
//    }
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.familyName);
        hash = 89 * hash + Objects.hashCode(this.familyGroupId);
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
        final UniprotFamilies other = (UniprotFamilies) obj;
        if (!Objects.equals(this.familyName, other.familyName)) {
            return false;
        }
        return Objects.equals(this.familyGroupId, other.familyGroupId);
    }

    @Override
    public String toString() {
        return "UniprotFamilies{" + "familyName=" + familyName + ", uniprotFamilyId=" + familyGroupId + '}';
    }

    public UniprotEntryEnzyme getAccession() {
        return accession;
    }

    public void setAccession(UniprotEntryEnzyme accession) {
        this.accession = accession;
    }

    public String getFamilyGroupId() {
        return familyGroupId;
    }

    public void setFamilyGroupId(String familyGroupId) {
        this.familyGroupId = familyGroupId;
    }

//    public UniqueFamilyName getFamilyNameId() {
//        return familyNameId;
//    }
//
//    public void setFamilyNameId(UniqueFamilyName familyNameId) {
//        this.familyNameId = familyNameId;
//    }
}
