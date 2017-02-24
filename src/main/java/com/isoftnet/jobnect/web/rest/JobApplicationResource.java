package com.isoftnet.jobnect.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.isoftnet.jobnect.domain.JobApplication;
import com.isoftnet.jobnect.domain.User;
import com.isoftnet.jobnect.domain.enumeration.Status;
import com.isoftnet.jobnect.service.JobApplicationService;
import com.isoftnet.jobnect.service.MailService;
import com.isoftnet.jobnect.service.UserService;
import com.isoftnet.jobnect.service.dto.MessageDTO;
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
 * REST controller for managing JobApplication.
 */
@RestController
@RequestMapping("/api")
public class JobApplicationResource {

    private final Logger log = LoggerFactory.getLogger(JobApplicationResource.class);
        
    @Inject
    private JobApplicationService jobApplicationService;
    
    @Inject
    private UserService userService;
    
    @Inject
    private MailService mailService;

    /**
     * POST  /job-applications : Create a new jobApplication.
     *
     * @param jobApplication the jobApplication to create
     * @return the ResponseEntity with status 201 (Created) and with body the new jobApplication, or with status 400 (Bad Request) if the jobApplication has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/job-applications")
    @Timed
    public ResponseEntity<JobApplication> createJobApplication(@Valid @RequestBody JobApplication jobApplication) throws URISyntaxException {
        log.debug("REST request to save JobApplication : {}", jobApplication);
        if (jobApplication.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("jobApplication", "idexists", "A new jobApplication cannot already have an ID")).body(null);
        }
        
        jobApplication.setCreatedOn(ZonedDateTime.now());
        jobApplication.setUpdatedOn(ZonedDateTime.now());
        JobApplication result = jobApplicationService.save(jobApplication);
        return ResponseEntity.created(new URI("/api/job-applications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("jobApplication", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /job-applications : Updates an existing jobApplication.
     *
     * @param jobApplication the jobApplication to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated jobApplication,
     * or with status 400 (Bad Request) if the jobApplication is not valid,
     * or with status 500 (Internal Server Error) if the jobApplication couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/job-applications")
    @Timed
    public ResponseEntity<JobApplication> updateJobApplication(@Valid @RequestBody JobApplication jobApplication) throws URISyntaxException {
        log.debug("REST request to update JobApplication : {}", jobApplication);
        if (jobApplication.getId() == null) {
            return createJobApplication(jobApplication);
        }
        
        jobApplication.setUpdatedOn(ZonedDateTime.now());
        JobApplication result = jobApplicationService.save(jobApplication);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("jobApplication", jobApplication.getId().toString()))
            .body(result);
    }

    /**
     * GET  /job-applications : get all the jobApplications.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of jobApplications in body
     */
    @GetMapping("/job-applications")
    @Timed
    public List<JobApplication> getAllJobApplications() {
        log.debug("REST request to get all JobApplications");
        return jobApplicationService.findAll();
    }

    /**
     * GET  /job-applications/:id : get the "id" jobApplication.
     *
     * @param id the id of the jobApplication to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the jobApplication, or with status 404 (Not Found)
     */
    @GetMapping("/job-applications/{id}")
    @Timed
    public ResponseEntity<JobApplication> getJobApplication(@PathVariable Long id) {
        log.debug("REST request to get JobApplication : {}", id);
        JobApplication jobApplication = jobApplicationService.findOne(id);
        return Optional.ofNullable(jobApplication)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /job-applications/:id : delete the "id" jobApplication.
     *
     * @param id the id of the jobApplication to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/job-applications/{id}")
    @Timed
    public ResponseEntity<Void> deleteJobApplication(@PathVariable Long id) {
        log.debug("REST request to delete JobApplication : {}", id);
        jobApplicationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("jobApplication", id.toString())).build();
    }
    
    @PostMapping("/message")
    @Timed
    public ResponseEntity<?> sendMessage(@Valid @RequestBody MessageDTO message) throws URISyntaxException {
        log.debug("REST request to send Message to Applicant : {}", message);
        
        JobApplication jobApplication = jobApplicationService.findOne(message.getId());
        User user = userService.getUserWithAuthorities(jobApplication.getUserId());
        
        // send email to user
        mailService.sendEmail(user.getEmail(), "Job Application", message.getMessage(), false, false);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @PostMapping("/application/received")
    @Timed
    public ResponseEntity<?> ackReceived(@Valid @RequestBody Long applicationId) throws URISyntaxException {
        log.debug("REST ackReceived : {}", applicationId);
        
        JobApplication jobApplication = jobApplicationService.findOne(applicationId);
        User user = userService.getUserWithAuthorities(jobApplication.getUserId());
        
        // send email to user
        mailService.sendEmail(user.getEmail(), "Job Application", "Application Received", false, false);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @PostMapping("/application/reject")
    @Timed
    public ResponseEntity<?> ackRejected(@Valid @RequestBody Long applicationId) throws URISyntaxException {
        log.debug("REST ackReceived : {}", applicationId);
        
        JobApplication jobApplication = jobApplicationService.findOne(applicationId);
        jobApplication.setStatus(Status.REJECTED);
        jobApplicationService.save(jobApplication);
         
        // send email to user
        User user = userService.getUserWithAuthorities(jobApplication.getUserId());
        mailService.sendEmail(user.getEmail(), "Job Application", "Application Rejected", false, false);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
