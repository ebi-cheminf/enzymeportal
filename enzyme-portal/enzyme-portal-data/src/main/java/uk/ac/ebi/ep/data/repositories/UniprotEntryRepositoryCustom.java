/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.ebi.ep.data.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.NoRepositoryBean;
import uk.ac.ebi.ep.data.domain.UniprotEntry;

/**
 *
 * @author joseph
 */
@NoRepositoryBean
public interface UniprotEntryRepositoryCustom {

    List<UniprotEntry> findEnzymesByNamePrefixes(List<String> nameprefixes);

    List<UniprotEntry> findEnzymesByAccessions(List<String> accessions);

    @EntityGraph(value = "UniprotEntryEntityGraph", type = EntityGraph.EntityGraphType.LOAD)
    UniprotEntry findByAccession(String accession);

    List<UniprotEntry> findEnzymesByAccession(String accession);

    List<UniprotEntry> findEnzymeByNamePrefixAndProteinName(String namePrefix, String proteinName);

    List<UniprotEntry> findEnzymeByAccessionsAndProteinName(List<String> accessions, String proteinName);

    List<String> filterEnzymesInAccessions(List<String> accessions);



}
