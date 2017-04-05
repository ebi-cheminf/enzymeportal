package uk.ac.ebi.ep.data.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author Joseph <joseph@ebi.ac.uk>
 */
@Entity
@Table(name = "PROTEIN_GROUPS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProteinGroups.findAll", query = "SELECT p FROM ProteinGroups p"),
    @NamedQuery(name = "ProteinGroups.findByProteinName", query = "SELECT p FROM ProteinGroups p WHERE p.proteinName = :proteinName"),
    @NamedQuery(name = "ProteinGroups.findByProteinGroupId", query = "SELECT p FROM ProteinGroups p WHERE p.proteinGroupId = :proteinGroupId")})
public class ProteinGroups implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 4000)
    @Column(name = "PROTEIN_NAME")
    private String proteinName;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "PROTEIN_GROUP_ID")
    private String proteinGroupId;
    @OneToMany(mappedBy = "proteinGroupId")
    @Fetch(FetchMode.JOIN)
    private List<UniprotEntry> uniprotEntryList;

    public ProteinGroups() {
    }

    public ProteinGroups(String proteinGroupId) {
        this.proteinGroupId = proteinGroupId;
    }

    public String getProteinName() {
        return proteinName;
    }

    public void setProteinName(String proteinName) {
        this.proteinName = proteinName;
    }

    public String getProteinGroupId() {
        return proteinGroupId;
    }

    public void setProteinGroupId(String proteinGroupId) {
        this.proteinGroupId = proteinGroupId;
    }

    @XmlTransient
    public List<UniprotEntry> getUniprotEntryList() {
        if (uniprotEntryList == null) {
            uniprotEntryList = new ArrayList<>();
        }
        return uniprotEntryList;
    }

    public void setUniprotEntryList(List<UniprotEntry> uniprotEntryList) {
        this.uniprotEntryList = uniprotEntryList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (proteinGroupId != null ? proteinGroupId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ProteinGroups)) {
            return false;
        }
        ProteinGroups other = (ProteinGroups) object;
        return !((this.proteinGroupId == null && other.proteinGroupId != null) || (this.proteinGroupId != null && !this.proteinGroupId.equals(other.proteinGroupId)));
    }

    @Override
    public String toString() {
        return "uk.ac.ebi.ep.data.domain.ProteinGroups[ proteinGroupId=" + proteinGroupId + " ]";
    }

}