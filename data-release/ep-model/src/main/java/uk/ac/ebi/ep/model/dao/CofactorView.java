package uk.ac.ebi.ep.model.dao;

import org.springframework.data.web.ProjectedPayload;

/**
 *
 * @author joseph
 */
@ProjectedPayload
public interface CofactorView {

    String getCompoundName();

    String getCompoundId();
}
