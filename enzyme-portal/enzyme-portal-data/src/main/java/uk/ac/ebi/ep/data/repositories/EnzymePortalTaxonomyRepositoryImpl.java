/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.ebi.ep.data.repositories;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.ep.data.domain.QEnzymesToTaxonomy;
import uk.ac.ebi.ep.data.search.model.Taxonomy;

/**
 *
 * @author Joseph <joseph@ebi.ac.uk>
 */
public class EnzymesToTaxonomyRepositoryImpl implements EnzymesToTaxonomyRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;
    private static final QEnzymesToTaxonomy $ = QEnzymesToTaxonomy.enzymesToTaxonomy;

    @Transactional(readOnly = true)
    @Override
    public List<Taxonomy> getCountForOrganisms(List<Long> taxids) {

        // JPAQuery query = new JPAQuery(entityManager);
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        List<Taxonomy> result = jpaQueryFactory
                .select(Projections.constructor(Taxonomy.class, $.taxId, $.scientificName, $.commonName, $.taxId.count()))
                .from($)
                .where($.taxId.in(taxids))
                .distinct()
                .groupBy($.taxId, $.scientificName, $.commonName)
                .fetch();

        return result;
    }
}
