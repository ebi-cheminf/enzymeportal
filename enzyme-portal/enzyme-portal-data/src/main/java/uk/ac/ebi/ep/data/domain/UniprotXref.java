/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.ebi.ep.data.domain;

import java.io.Serializable;
import java.math.BigDecimal;
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

/**
 *
 * @author <a href="mailto:joseph@ebi.ac.uk">Joseph</a>
 */
@Entity
@Table(name = "UNIPROT_XREF")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UniprotXref.findAll", query = "SELECT u FROM UniprotXref u"),
    @NamedQuery(name = "UniprotXref.findByXrefId", query = "SELECT u FROM UniprotXref u WHERE u.xrefId = :xrefId"),
    @NamedQuery(name = "UniprotXref.findByDbentryId", query = "SELECT u FROM UniprotXref u WHERE u.dbentryId = :dbentryId"),
    @NamedQuery(name = "UniprotXref.findBySourceId", query = "SELECT u FROM UniprotXref u WHERE u.sourceId = :sourceId"),
    @NamedQuery(name = "UniprotXref.findBySource", query = "SELECT u FROM UniprotXref u WHERE u.source = :source"),
    @NamedQuery(name = "UniprotXref.findBySourceName", query = "SELECT u FROM UniprotXref u WHERE u.sourceName = :sourceName")})
public class UniprotXref implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "XREF_ID")
    private BigDecimal xrefId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DBENTRY_ID")
    private long dbentryId;
    @Size(max = 60)
    @Column(name = "SOURCE_ID")
    private String sourceId;
    @Size(max = 8)
    @Column(name = "SOURCE")
    private String source;
    @Size(max = 4000)
    @Column(name = "SOURCE_NAME")
    private String sourceName;
    @JoinColumn(name = "ACCESSION", referencedColumnName = "ACCESSION")
    @ManyToOne(optional = false)
    private UniprotEntry accession;

    public UniprotXref() {
    }

    public UniprotXref(BigDecimal xrefId) {
        this.xrefId = xrefId;
    }

    public UniprotXref(BigDecimal xrefId, long dbentryId) {
        this.xrefId = xrefId;
        this.dbentryId = dbentryId;
    }

    public BigDecimal getXrefId() {
        return xrefId;
    }

    public void setXrefId(BigDecimal xrefId) {
        this.xrefId = xrefId;
    }

    public long getDbentryId() {
        return dbentryId;
    }

    public void setDbentryId(long dbentryId) {
        this.dbentryId = dbentryId;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public UniprotEntry getAccession() {
        return accession;
    }

    public void setAccession(UniprotEntry accession) {
        this.accession = accession;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (xrefId != null ? xrefId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UniprotXref)) {
            return false;
        }
        UniprotXref other = (UniprotXref) object;
        if ((this.xrefId == null && other.xrefId != null) || (this.xrefId != null && !this.xrefId.equals(other.xrefId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uk.ac.ebi.ep.data.domain.UniprotXref[ xrefId=" + xrefId + " ]";
    }
    
}
