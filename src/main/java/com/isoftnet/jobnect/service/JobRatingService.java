package com.isoftnet.jobnect.service;

import com.isoftnet.jobnect.domain.JobRating;
import java.util.List;

/**
 * Service Interface for managing JobRating.
 */
public interface JobRatingService {

    /**
     * Save a jobRating.
     *
     * @param jobRating the entity to save
     * @return the persisted entity
     */
    JobRating save(JobRating jobRating);

    /**
     *  Get all the jobRatings.
     *  
     *  @return the list of entities
     */
    List<JobRating> findAll();

    /**
     *  Get the "id" jobRating.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    JobRating findOne(Long id);

    /**
     *  Delete the "id" jobRating.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
