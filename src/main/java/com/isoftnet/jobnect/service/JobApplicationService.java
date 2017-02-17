package com.isoftnet.jobnect.service;

import com.isoftnet.jobnect.domain.JobApplication;
import java.util.List;

/**
 * Service Interface for managing JobApplication.
 */
public interface JobApplicationService {

    /**
     * Save a jobApplication.
     *
     * @param jobApplication the entity to save
     * @return the persisted entity
     */
    JobApplication save(JobApplication jobApplication);

    /**
     *  Get all the jobApplications.
     *  
     *  @return the list of entities
     */
    List<JobApplication> findAll();

    /**
     *  Get the "id" jobApplication.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    JobApplication findOne(Long id);

    /**
     *  Delete the "id" jobApplication.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
