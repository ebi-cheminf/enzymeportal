
package uk.ac.ebi.webservices.ebeye;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="domain" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="entry" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="referencedDomain" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "domain",
    "entry",
    "referencedDomain"
})
@XmlRootElement(name = "getReferencedEntries")
public class GetReferencedEntries {

    @XmlElement(required = true, nillable = true)
    protected String domain;
    @XmlElement(required = true, nillable = true)
    protected String entry;
    @XmlElement(required = true, nillable = true)
    protected String referencedDomain;

    /**
     * Gets the value of the domain property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDomain() {
        return domain;
    }

    /**
     * Sets the value of the domain property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDomain(String value) {
        this.domain = value;
    }

    /**
     * Gets the value of the entry property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntry() {
        return entry;
    }

    /**
     * Sets the value of the entry property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntry(String value) {
        this.entry = value;
    }

    /**
     * Gets the value of the referencedDomain property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferencedDomain() {
        return referencedDomain;
    }

    /**
     * Sets the value of the referencedDomain property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferencedDomain(String value) {
        this.referencedDomain = value;
    }

}
