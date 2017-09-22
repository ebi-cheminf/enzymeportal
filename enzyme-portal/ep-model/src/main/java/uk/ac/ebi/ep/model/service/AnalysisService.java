/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.ebi.ep.model.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.ep.model.SpEnzymeEvidence;
import uk.ac.ebi.ep.model.repositories.SpEnzymeEvidenceRepository;
import uk.ac.ebi.ep.model.repositories.UniprotEntryRepository;

/**
 *
 * @author Joseph <joseph@ebi.ac.uk>
 */
@Transactional
@Service
public class AnalysisService {

    @Autowired
    private SpEnzymeEvidenceRepository enzymeEvidenceRepository;
    @Autowired
    private UniprotEntryRepository uniprotEntryRepository;

    public void populateEvidences(List<SpEnzymeEvidence> evidences) {
        enzymeEvidenceRepository.save(evidences);
    }

    @Transactional(readOnly = true)
    public List<String> findAllSwissProtAccessions() {

        return uniprotEntryRepository.findSwissProtAccessions();
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Modifying
    public void updateExpEvidenceFlag() {
        uniprotEntryRepository.updateExpEvidenceFlag();
    }

}
