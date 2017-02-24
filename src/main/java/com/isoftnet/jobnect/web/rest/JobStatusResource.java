package com.isoftnet.jobnect.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.isoftnet.jobnect.domain.JobStatus;
import com.isoftnet.jobnect.domain.User;
import com.isoftnet.jobnect.service.JobStatusService;
import com.isoftnet.jobnect.service.UserService;
import com.isoftnet.jobnect.service.dto.JobStatusDTO;
import com.isoftnet.jobnect.web.rest.util.HeaderUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing JobStatus.
 */
@RestController
@RequestMapping("/api")
public class JobStatusResource {

    private final Logger log = LoggerFactory.getLogger(JobStatusResource.class);
        
    @Inject
    private JobStatusService jobStatusService;
    
    @Inject
    private UserService userService;

    /**
     * POST  /job-statuses : Create a new jobStatus.
     *
     * @param jobStatus the jobStatus to create
     * @return the ResponseEntity with status 201 (Created) and with body the new jobStatus, or with status 400 (Bad Request) if the jobStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/job-statuses")
    @Timed
    public ResponseEntity<JobStatus> createJobStatus(@Valid @RequestBody JobStatus jobStatus) throws URISyntaxException {
        log.debug("REST request to save JobStatus : {}", jobStatus);
        if (jobStatus.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("jobStatus", "idexists", "A new jobStatus cannot already have an ID")).body(null);
        }
        
        User user = userService.getUserWithAuthorities();
        jobStatus.setCreatedBy(String.format("%s %s", user.getFirstName(), user.getLastName()));
        jobStatus.setCreatedOn(ZonedDateTime.now());
		jobStatus.setUpdatedOn(ZonedDateTime.now());
        JobStatus result = jobStatusService.save(jobStatus);
        return ResponseEntity.created(new URI("/api/job-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("jobStatus", result.getId().toString()))
            .body(result);
    }
    
    @PostMapping("/addJobStatus")
    @Timed
    public ResponseEntity<?> addJobStatus(@Valid @RequestBody JobStatusDTO jobStatusDTO) throws URISyntaxException {
        
    	log.debug("REST request to save JobStatus(es) : {}", jobStatusDTO);
    	
    	User user = userService.getUserWithAuthorities();
    	for(Long jobId : jobStatusDTO.getJobIds())
    	{
    		log.info("@adding status for job " + jobId);
    		JobStatus jobStatus = new JobStatus();
    		jobStatus.setJobId(jobId);
    		jobStatus.setComment(jobStatusDTO.getComment());
    		jobStatus.setCreatedOn(ZonedDateTime.now());
    		jobStatus.setUpdatedOn(ZonedDateTime.now());
    		jobStatus.setCreatedBy(String.format("%s %s", user.getFirstName(), user.getLastName()));
    		jobStatusService.save(jobStatus);
    	}
    	
    	return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * PUT  /job-statuses : Updates an existing jobStatus.
     *
     * @param jobStatus the jobStatus to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated jobStatus,
     * or with status 400 (Bad Request) if the jobStatus is not valid,
     * or with status 500 (Internal Server Error) if the jobStatus couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/job-statuses")
    @Timed
    public ResponseEntity<JobStatus> updateJobStatus(@Valid @RequestBody JobStatus jobStatus) throws URISyntaxException {
        log.debug("REST request to update JobStatus : {}", jobStatus);
        if (jobStatus.getId() == null) {
            return createJobStatus(jobStatus);
        }
        JobStatus result = jobStatusService.save(jobStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("jobStatus", jobStatus.getId().toString()))
            .body(result);
    }

    /**
     * GET  /job-statuses : get all the jobStatuses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of jobStatuses in body
     */
    @GetMapping("/job-statuses")
    @Timed
    public List<JobStatus> getAllJobStatuses() {
        log.debug("REST request to get all JobStatuses");
        return jobStatusService.findAll();
    }

    /**
     * GET  /job-statuses/:id : get the "id" jobStatus.
     *
     * @param id the id of the jobStatus to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the jobStatus, or with status 404 (Not Found)
     */
    @GetMapping("/job-statuses/{id}")
    @Timed
    public ResponseEntity<JobStatus> getJobStatus(@PathVariable Long id) {
        log.debug("REST request to get JobStatus : {}", id);
        JobStatus jobStatus = jobStatusService.findOne(id);
        return Optional.ofNullable(jobStatus)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /job-statuses/:id : delete the "id" jobStatus.
     *
     * @param id the id of the jobStatus to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/job-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteJobStatus(@PathVariable Long id) {
        log.debug("REST request to delete JobStatus : {}", id);
        jobStatusService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("jobStatus", id.toString())).build();
    }

}
