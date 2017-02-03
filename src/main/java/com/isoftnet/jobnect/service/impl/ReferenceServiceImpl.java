package com.isoftnet.jobnect.service.impl;

import com.isoftnet.jobnect.service.ReferenceService;
import com.isoftnet.jobnect.domain.Reference;
import com.isoftnet.jobnect.repository.ReferenceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Reference.
 */
@Service
@Transactional
public class ReferenceServiceImpl implements ReferenceService{

    private final Logger log = LoggerFactory.getLogger(ReferenceServiceImpl.class);
    
    @Inject
    private ReferenceRepository referenceRepository;

    /**
     * Save a reference.
     *
     * @param reference the entity to save
     * @return the persisted entity
     */
    public Reference save(Reference reference) {
        log.debug("Request to save Reference : {}", reference);
        Reference result = referenceRepository.save(reference);
        return result;
    }

    /**
     *  Get all the references.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Reference> findAll() {
        log.debug("Request to get all References");
        List<Reference> result = referenceRepository.findAll();

        return result;
    }

    /**
     *  Get one reference by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Reference findOne(Long id) {
        log.debug("Request to get Reference : {}", id);
        Reference reference = referenceRepository.findOne(id);
        return reference;
    }

    /**
     *  Delete the  reference by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Reference : {}", id);
        referenceRepository.delete(id);
    }
}
