package uk.ac.ebi.ep.data.search.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import uk.ac.ebi.biobabel.util.collections.ChemicalNameComparator;

/**
 * <p>
 * Java class for Disease complex type.
 *
 *
 *
 */
public class Disease implements Serializable {

    protected String id;

    protected String name;
    protected String description;

    protected Object url;
    protected boolean selected;
    protected int numEnzyme;

    protected List<String> evidences = new ArrayList<>();
    public static final Comparator<String> NAME_COMPARATOR
            = new ChemicalNameComparator();

    public Disease() {
    }

    public Disease(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Disease(String id, String name, Object url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public Disease(String id, String name, String description, Object url, String evidence) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.url = url;
        evidences.add(evidence);
    }

    public List<String> getEvidences() {
        if (evidences == null) {
            evidences = new ArrayList<>();
        }

        return evidences;
    }

    public void setEvidences(List<String> evidences) {
        this.evidences = evidences;
    }

    /**
     * Gets the value of the id property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the name property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the description property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the url property.
     *
     * @return possible object is {@link Object }
     *
     */
    public Object getUrl() {
        return url;
    }

    /**
     * Sets the value of the url property.
     *
     * @param value allowed object is {@link Object }
     *
     */
    public void setUrl(Object value) {
        this.url = value;
    }

    /**
     * Gets the value of the selected property.
     *
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Sets the value of the selected property.
     *
     */
    public void setSelected(boolean value) {
        this.selected = value;
    }

    /**
     * Gets the value of the numEnzyme property.
     *
     */
    public int getNumEnzyme() {
        return numEnzyme;
    }

    /**
     * Sets the value of the numEnzyme property.
     *
     */
    public void setNumEnzyme(int value) {
        this.numEnzyme = value;
    }

    public Disease withId(String value) {
        setId(value);
        return this;
    }

    public Disease withName(String value) {
        setName(value);
        return this;
    }

    public Disease withDescription(String value) {
        setDescription(value);
        return this;
    }

    public Disease withUrl(Object value) {
        setUrl(value);
        return this;
    }

    public Disease withSelected(boolean value) {
        setSelected(value);
        return this;
    }

    public Disease withNumEnzyme(int value) {
        setNumEnzyme(value);
        return this;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Disease other = (Disease) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Disease{" + "id=" + id + ", name=" + name + ", numEnzyme=" + numEnzyme + '}';
    }

}
