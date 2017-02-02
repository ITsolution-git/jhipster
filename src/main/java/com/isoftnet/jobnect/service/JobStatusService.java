package com.isoftnet.jobnect.service;

import com.isoftnet.jobnect.domain.JobStatus;
import java.util.List;

/**
 * Service Interface for managing JobStatus.
 */
public interface JobStatusService {

    /**
     * Save a jobStatus.
     *
     * @param jobStatus the entity to save
     * @return the persisted entity
     */
    JobStatus save(JobStatus jobStatus);

    /**
     *  Get all the jobStatuses.
     *  
     *  @return the list of entities
     */
    List<JobStatus> findAll();

    /**
     *  Get the "id" jobStatus.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    JobStatus findOne(Long id);

    /**
     *  Delete the "id" jobStatus.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
