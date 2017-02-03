package com.isoftnet.jobnect.service;

import com.isoftnet.jobnect.domain.Reference;
import java.util.List;

/**
 * Service Interface for managing Reference.
 */
public interface ReferenceService {

    /**
     * Save a reference.
     *
     * @param reference the entity to save
     * @return the persisted entity
     */
    Reference save(Reference reference);

    /**
     *  Get all the references.
     *  
     *  @return the list of entities
     */
    List<Reference> findAll();

    /**
     *  Get the "id" reference.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Reference findOne(Long id);

    /**
     *  Delete the "id" reference.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
