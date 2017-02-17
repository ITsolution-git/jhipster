package com.isoftnet.jobnect.service.impl;

import com.isoftnet.jobnect.service.JobStatusService;
import com.isoftnet.jobnect.domain.JobStatus;
import com.isoftnet.jobnect.repository.JobStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing JobStatus.
 */
@Service
@Transactional
public class JobStatusServiceImpl implements JobStatusService{

    private final Logger log = LoggerFactory.getLogger(JobStatusServiceImpl.class);
    
    @Inject
    private JobStatusRepository jobStatusRepository;

    /**
     * Save a jobStatus.
     *
     * @param jobStatus the entity to save
     * @return the persisted entity
     */
    public JobStatus save(JobStatus jobStatus) {
        log.debug("Request to save JobStatus : {}", jobStatus);
        JobStatus result = jobStatusRepository.save(jobStatus);
        return result;
    }

    /**
     *  Get all the jobStatuses.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<JobStatus> findAll() {
        log.debug("Request to get all JobStatuses");
        List<JobStatus> result = jobStatusRepository.findAll();

        return result;
    }

    /**
     *  Get one jobStatus by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public JobStatus findOne(Long id) {
        log.debug("Request to get JobStatus : {}", id);
        JobStatus jobStatus = jobStatusRepository.findOne(id);
        return jobStatus;
    }

    /**
     *  Delete the  jobStatus by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete JobStatus : {}", id);
        jobStatusRepository.delete(id);
    }
}
