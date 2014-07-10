/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.ebi.ep.data.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


/**
 *
 * @author joseph
 */
public class SearchResults {

    private Integer totalfound;
    private SearchFilters searchfilters;

    private List<EnzymeSummaries> summaryentries;

    /**
     * Gets the value of the searchfilters property.
     *
     * @return possible object is {@link SearchFilters }
     *
     */
    public SearchFilters getSearchfilters() {
        return searchfilters;
    }

    /**
     * Sets the value of the searchfilters property.
     *
     * @param value allowed object is {@link SearchFilters }
     *
     */
    public void setSearchfilters(SearchFilters value) {
        this.searchfilters = value;
    }

    /**
     * Gets the value of the summaryentries property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the summaryentries property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSummaryentries().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EnzymeSummary }
     *
     *
     * @return
     */
    public List<EnzymeSummaries> getSummaryentries() {
        if (summaryentries == null) {
            summaryentries = new ArrayList<EnzymeSummaries>();
        }
        return this.summaryentries;
    }

    /**
     * Gets the value of the totalfound property.
     *
     */
    public int getTotalfound() {
        return totalfound;
    }

    /**
     * Sets the value of the totalfound property.
     *
     */
    public void setTotalfound(int value) {
        this.totalfound = value;
    }

    public SearchResults withSearchfilters(SearchFilters value) {
        setSearchfilters(value);
        return this;
    }

    public SearchResults withSummaryentries(EnzymeSummaries... values) {
        if (values != null) {
            getSummaryentries().addAll(Arrays.asList(values));
        }
        return this;
    }

    public SearchResults withSummaryentries(Collection<EnzymeSummaries> values) {
        if (values != null) {
            getSummaryentries().addAll(values);
        }
        return this;
    }

    public SearchResults withTotalfound(int value) {
        setTotalfound(value);
        return this;
    }

    /**
     * Sets the value of the summaryentries property.
     *
     * @param summaryentries allowed object is {@link EnzymeSummary }
     *
     */
    public void setSummaryentries(List<EnzymeSummaries> summaryentries) {
        this.summaryentries = summaryentries;
    }

}