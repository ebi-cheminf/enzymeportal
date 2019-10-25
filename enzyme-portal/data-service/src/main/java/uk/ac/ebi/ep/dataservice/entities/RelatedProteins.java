/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.ebi.ep.dataservice.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author joseph
 */
@Entity
@Table(name = "RELATED_PROTEINS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RelatedProteins.findAll", query = "SELECT r FROM RelatedProteins r"),
    @NamedQuery(name = "RelatedProteins.findByRelProtInternalId", query = "SELECT r FROM RelatedProteins r WHERE r.relProtInternalId = :relProtInternalId"),
    @NamedQuery(name = "RelatedProteins.findByNamePrefix", query = "SELECT r FROM RelatedProteins r WHERE r.namePrefix = :namePrefix")})
public class RelatedProteins implements Serializable {

    @Size(max = 120)
    @Column(name = "NAME_PREFIX")
    private String namePrefix;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Column(name = "REL_PROT_INTERNAL_ID")
    private BigDecimal relProtInternalId;
    @OneToMany(mappedBy = "relatedProteinsId")
    private Set<UniprotEntry> uniprotEntrySet;

    public RelatedProteins() {
    }

    public RelatedProteins(BigDecimal relProtInternalId) {
        this.relProtInternalId = relProtInternalId;
    }

    public BigDecimal getRelProtInternalId() {
        return relProtInternalId;
    }

    public void setRelProtInternalId(BigDecimal relProtInternalId) {
        this.relProtInternalId = relProtInternalId;
    }

    public String getNamePrefix() {
        return namePrefix;
    }

    public void setNamePrefix(String namePrefix) {
        this.namePrefix = namePrefix;
    }

    @XmlTransient
    public Set<UniprotEntry> getUniprotEntrySet() {
        return uniprotEntrySet;
    }

    public void setUniprotEntrySet(Set<UniprotEntry> uniprotEntrySet) {
        this.uniprotEntrySet = uniprotEntrySet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (relProtInternalId != null ? relProtInternalId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RelatedProteins)) {
            return false;
        }
        RelatedProteins other = (RelatedProteins) object;
        if ((this.relProtInternalId == null && other.relProtInternalId != null) || (this.relProtInternalId != null && !this.relProtInternalId.equals(other.relProtInternalId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uk.ac.ebi.ep.dataservice.entities.RelatedProteins[ relProtInternalId=" + relProtInternalId + " ]";
    }

    
}