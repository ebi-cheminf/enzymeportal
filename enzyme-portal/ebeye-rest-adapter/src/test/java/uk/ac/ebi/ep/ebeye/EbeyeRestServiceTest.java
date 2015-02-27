/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.ebi.ep.ebeye;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.io.IOUtils;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import uk.ac.ebi.ep.ebeye.autocomplete.EbeyeAutocomplete;
import uk.ac.ebi.ep.ebeye.autocomplete.Suggestion;
import uk.ac.ebi.ep.ebeye.search.EbeyeSearchResult;
import uk.ac.ebi.ep.ebeye.search.Entry;

/**
 *
 * @author joseph
 */
public class EbeyeRestServiceTest extends EbeyeBaseTest {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(EbeyeRestServiceTest.class);

    @Autowired
    private EbeyeRestService ebeyeRestService;

    /**
     * Test of ebeyeAutocompleteSearch method, of class EbeyeRestService.
     */
    @Test
    public void testEbeyeAutocompleteSearch() {
        try {
            LOGGER.info("ebeyeAutocompleteSearch");

            String searchTerm = "phos";

            String url = ebeyeIndexUrl.getDefaultSearchIndexUrl() + "/autocomplete?term=" + searchTerm + "&format=json";

            InputStream in = this.getClass().getClassLoader()
                    .getResourceAsStream("suggestions.json");

            String json = IOUtils.toString(in);
            mockRestServer.expect(requestTo(url)).andExpect(method(HttpMethod.GET))
                    .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));

            EbeyeAutocomplete expResult = restTemplate.getForObject(url.trim(), EbeyeAutocomplete.class);

            List<Suggestion> result = ebeyeRestService.ebeyeAutocompleteSearch(searchTerm);

            String suggestion = expResult.getSuggestions().stream().findAny().get().getSuggestion();

            mockRestServer.verify();

            assertThat(result.stream().findAny().get().getSuggestion(), containsString(suggestion));

            assertEquals(expResult.getSuggestions(), result);
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }

    }

    /**
     * Test of queryEbeyeForAccessions method, of class EbeyeRestService.
     */
    @Test
    public void testQueryEbeyeForAccessions_String() {
        try {
            LOGGER.info("queryEbeyeForAccessions");

            String query = "sildenafil";

            String url = ebeyeIndexUrl.getDefaultSearchIndexUrl() + "?format=json&size=100&query=";

            InputStream in = this.getClass().getClassLoader()
                    .getResourceAsStream("ebeye.json");

            String json = IOUtils.toString(in);
            mockRestServer.expect(requestTo(url)).andExpect(method(HttpMethod.GET))
                    .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));

            EbeyeSearchResult searchResult = restTemplate.getForObject(url.trim(), EbeyeSearchResult.class);

            //EbeyeSearchResult searchResult = queryEbeye(query);
            Set<String> accessions = new LinkedHashSet<>();

            for (Entry entry : searchResult.getEntries()) {
                accessions.add(entry.getUniprot_accession());
            }

            List<String> expResult = accessions.stream().distinct().collect(Collectors.toList());

            String accession = expResult.stream().findAny().get();

            List<String> result = ebeyeRestService.queryEbeyeForAccessions(query);

            mockRestServer.verify();

            assertThat(result.stream().findAny().get(), containsString(accession));

            assertEquals(expResult, result);
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }

    }

    /**
     * Test of queryEbeyeForAccessions method, of class EbeyeRestService.
     */
    @Test
    public void testQueryEbeyeForAccessions_String_boolean() {
        LOGGER.info("queryEbeyeForAccessions paginate:false");

        try {

            boolean paginate = false;
            String query = "sildenafil";

            String url = ebeyeIndexUrl.getDefaultSearchIndexUrl() + "?format=json&size=100&query=";

            InputStream in = this.getClass().getClassLoader()
                    .getResourceAsStream("ebeye.json");

            String json = IOUtils.toString(in);
            mockRestServer.expect(requestTo(url)).andExpect(method(HttpMethod.GET))
                    .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));

            EbeyeSearchResult searchResult = restTemplate.getForObject(url.trim(), EbeyeSearchResult.class);

            //EbeyeSearchResult searchResult = queryEbeye(query);
            Set<String> accessions = new LinkedHashSet<>();

            for (Entry entry : searchResult.getEntries()) {
                accessions.add(entry.getUniprot_accession());
            }

            List<String> expResult = accessions.stream().distinct().collect(Collectors.toList());

            String accession = expResult.stream().findAny().get();

            List<String> result = ebeyeRestService.queryEbeyeForAccessions(query, paginate);

            mockRestServer.verify();

            assertThat(result.stream().findAny().get(), containsString(accession));

            assertEquals(expResult, result);
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }

    }

    /**
     * Test of queryEbeyeForAccessions method, of class EbeyeRestService.
     */
    @Test
    public void testQueryEbeyeForAccessions_3args() {
        System.out.println("queryEbeyeForAccessions paginate :true:limit:yes");

        try {

            int limit = 2;
            boolean paginate = true;
            String query = "sildenafil";

            String url = ebeyeIndexUrl.getDefaultSearchIndexUrl() + "?format=json&size=100&query=";

            InputStream in = this.getClass().getClassLoader()
                    .getResourceAsStream("ebeye.json");

            String json = IOUtils.toString(in);
            mockRestServer.expect(requestTo(url)).andExpect(method(HttpMethod.GET))
                    .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));

            EbeyeSearchResult searchResult = restTemplate.getForObject(url.trim(), EbeyeSearchResult.class);

            Set<String> accessions = new LinkedHashSet<>();

            for (Entry entry : searchResult.getEntries()) {
                accessions.add(entry.getUniprot_accession());
            }

            List<String> expResult = accessions.stream().distinct().collect(Collectors.toList());

            String accession = expResult.stream().findAny().get();

            List<String> result = ebeyeRestService.queryEbeyeForAccessions(query, paginate, limit);

            mockRestServer.verify();

            assertThat(result.stream().findAny().get(), containsString(accession));

            assertEquals(expResult, result);
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }

    }

}