package com.isoftnet.jobnect.service;

import com.isoftnet.jobnect.domain.LifeCycle;
import java.util.List;

/**
 * Service Interface for managing LifeCycle.
 */
public interface LifeCycleService {

    /**
     * Save a lifeCycle.
     *
     * @param lifeCycle the entity to save
     * @return the persisted entity
     */
    LifeCycle save(LifeCycle lifeCycle);

    /**
     *  Get all the lifeCycles.
     *  
     *  @return the list of entities
     */
    List<LifeCycle> findAll();

    /**
     *  Get the "id" lifeCycle.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    LifeCycle findOne(Long id);

    /**
     *  Delete the "id" lifeCycle.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
