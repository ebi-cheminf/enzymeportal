/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.ebi.ep.data.repositories;

import com.mysema.query.jpa.impl.JPAQuery;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import uk.ac.ebi.ep.data.domain.EnzymePortalEcNumbers;
import uk.ac.ebi.ep.data.domain.QEnzymePortalEcNumbers;

/**
 *
 * @author joseph
 */
public class EnzymePortalEcNumbersRepositoryImpl implements EnzymePortalEcNumbersRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;
    private static final QEnzymePortalEcNumbers $ = QEnzymePortalEcNumbers.enzymePortalEcNumbers;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<String> findAccessionsByEc(String ecNumber) {
        JPAQuery query = new JPAQuery(entityManager);
        //List<String> enzymes = new ArrayList<>();

        List<String> enzymes = query.from($).where($.ecNumber.equalsIgnoreCase(ecNumber))
                .list($.uniprotAccession.accession).stream().collect(Collectors.toList());

//              
//              List<EnzymePortalEcNumbers> ecs = query.from($).where($.ecNumber.equalsIgnoreCase(ecNumber)).distinct().list($).stream().collect(Collectors.toList());
//             
//              ecs.stream().forEach((ec) -> {
//                  enzymes.add(ec.getUniprotAccession().getAccession());
//            });
        return enzymes;
    }

    @Override
    public List<EnzymePortalEcNumbers> findByEcNumber(String ecNumber) {
        JPAQuery query = new JPAQuery(entityManager);
        List<EnzymePortalEcNumbers> ecs = query.from($).where($.ecNumber.equalsIgnoreCase(ecNumber)).distinct().list($);

        return ecs.stream().collect(Collectors.toList());
    }
}
