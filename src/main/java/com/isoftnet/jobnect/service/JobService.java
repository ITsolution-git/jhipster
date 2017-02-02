package com.isoftnet.jobnect.service;

import com.isoftnet.jobnect.domain.Job;
import java.util.List;

/**
 * Service Interface for managing Job.
 */
public interface JobService {

    /**
     * Save a job.
     *
     * @param job the entity to save
     * @return the persisted entity
     */
    Job save(Job job);

    /**
     *  Get all the jobs.
     *  
     *  @return the list of entities
     */
    List<Job> findAll();

    /**
     *  Get the "id" job.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Job findOne(Long id);

    /**
     *  Delete the "id" job.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
