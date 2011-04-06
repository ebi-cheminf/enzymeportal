//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.04.05 at 05:35:53 PM BST 
//


package uk.ac.ebi.ep.search.result;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EnzymeSummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EnzymeSummary">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ebi.ac.uk/enzymeportal/result}EnzymeAccession">
 *       &lt;sequence>
 *         &lt;element name="ec" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="function" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="synonyms" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *         &lt;element name="pdbeaccession" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="uniprotid" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Compound" type="{http://ebi.ac.uk/enzymeportal/result}Compound" maxOccurs="unbounded"/>
 *         &lt;element name="Disease" type="{http://ebi.ac.uk/enzymeportal/result}Disease" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="RelatedEnzymeCollection" type="{http://ebi.ac.uk/enzymeportal/result}RelatedEnzymeCollection"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EnzymeSummary", propOrder = {
    "ec",
    "name",
    "function",
    "synonyms",
    "pdbeaccession",
    "uniprotid",
    "compound",
    "disease",
    "relatedEnzymeCollection"
})
public class EnzymeSummary
    extends EnzymeAccession
{

    @XmlElement(required = true)
    protected String ec;
    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected String function;
    @XmlElement(required = true)
    protected List<String> synonyms;
    @XmlElement(required = true)
    protected String pdbeaccession;
    @XmlElement(required = true)
    protected String uniprotid;
    @XmlElement(name = "Compound", required = true)
    protected List<Compound> compound;
    @XmlElement(name = "Disease")
    protected List<Disease> disease;
    @XmlElement(name = "RelatedEnzymeCollection", required = true)
    protected RelatedEnzymeCollection relatedEnzymeCollection;

    /**
     * Gets the value of the ec property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEc() {
        return ec;
    }

    /**
     * Sets the value of the ec property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEc(String value) {
        this.ec = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the function property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFunction() {
        return function;
    }

    /**
     * Sets the value of the function property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFunction(String value) {
        this.function = value;
    }

    /**
     * Gets the value of the synonyms property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the synonyms property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSynonyms().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getSynonyms() {
        if (synonyms == null) {
            synonyms = new ArrayList<String>();
        }
        return this.synonyms;
    }

    /**
     * Gets the value of the pdbeaccession property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPdbeaccession() {
        return pdbeaccession;
    }

    /**
     * Sets the value of the pdbeaccession property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPdbeaccession(String value) {
        this.pdbeaccession = value;
    }

    /**
     * Gets the value of the uniprotid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUniprotid() {
        return uniprotid;
    }

    /**
     * Sets the value of the uniprotid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUniprotid(String value) {
        this.uniprotid = value;
    }

    /**
     * Gets the value of the compound property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the compound property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCompound().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Compound }
     * 
     * 
     */
    public List<Compound> getCompound() {
        if (compound == null) {
            compound = new ArrayList<Compound>();
        }
        return this.compound;
    }

    /**
     * Gets the value of the disease property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the disease property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDisease().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Disease }
     * 
     * 
     */
    public List<Disease> getDisease() {
        if (disease == null) {
            disease = new ArrayList<Disease>();
        }
        return this.disease;
    }

    /**
     * Gets the value of the relatedEnzymeCollection property.
     * 
     * @return
     *     possible object is
     *     {@link RelatedEnzymeCollection }
     *     
     */
    public RelatedEnzymeCollection getRelatedEnzymeCollection() {
        return relatedEnzymeCollection;
    }

    /**
     * Sets the value of the relatedEnzymeCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link RelatedEnzymeCollection }
     *     
     */
    public void setRelatedEnzymeCollection(RelatedEnzymeCollection value) {
        this.relatedEnzymeCollection = value;
    }

}
