package com.isoftnet.jobnect.service.impl;

import com.isoftnet.jobnect.service.JobApplicationService;
import com.isoftnet.jobnect.domain.JobApplication;
import com.isoftnet.jobnect.repository.JobApplicationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing JobApplication.
 */
@Service
@Transactional
public class JobApplicationServiceImpl implements JobApplicationService{

    private final Logger log = LoggerFactory.getLogger(JobApplicationServiceImpl.class);
    
    @Inject
    private JobApplicationRepository jobApplicationRepository;

    /**
     * Save a jobApplication.
     *
     * @param jobApplication the entity to save
     * @return the persisted entity
     */
    public JobApplication save(JobApplication jobApplication) {
        log.debug("Request to save JobApplication : {}", jobApplication);
        JobApplication result = jobApplicationRepository.save(jobApplication);
        return result;
    }

    /**
     *  Get all the jobApplications.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<JobApplication> findAll() {
        log.debug("Request to get all JobApplications");
        List<JobApplication> result = jobApplicationRepository.findAll();

        return result;
    }

    /**
     *  Get one jobApplication by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public JobApplication findOne(Long id) {
        log.debug("Request to get JobApplication : {}", id);
        JobApplication jobApplication = jobApplicationRepository.findOne(id);
        return jobApplication;
    }

    /**
     *  Delete the  jobApplication by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete JobApplication : {}", id);
        jobApplicationRepository.delete(id);
    }
}
