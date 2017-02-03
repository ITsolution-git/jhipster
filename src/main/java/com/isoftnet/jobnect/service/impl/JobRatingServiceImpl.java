package com.isoftnet.jobnect.service.impl;

import com.isoftnet.jobnect.service.JobRatingService;
import com.isoftnet.jobnect.domain.JobRating;
import com.isoftnet.jobnect.repository.JobRatingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing JobRating.
 */
@Service
@Transactional
public class JobRatingServiceImpl implements JobRatingService{

    private final Logger log = LoggerFactory.getLogger(JobRatingServiceImpl.class);
    
    @Inject
    private JobRatingRepository jobRatingRepository;

    /**
     * Save a jobRating.
     *
     * @param jobRating the entity to save
     * @return the persisted entity
     */
    public JobRating save(JobRating jobRating) {
        log.debug("Request to save JobRating : {}", jobRating);
        JobRating result = jobRatingRepository.save(jobRating);
        return result;
    }

    /**
     *  Get all the jobRatings.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<JobRating> findAll() {
        log.debug("Request to get all JobRatings");
        List<JobRating> result = jobRatingRepository.findAll();

        return result;
    }

    /**
     *  Get one jobRating by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public JobRating findOne(Long id) {
        log.debug("Request to get JobRating : {}", id);
        JobRating jobRating = jobRatingRepository.findOne(id);
        return jobRating;
    }

    /**
     *  Delete the  jobRating by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete JobRating : {}", id);
        jobRatingRepository.delete(id);
    }
}
