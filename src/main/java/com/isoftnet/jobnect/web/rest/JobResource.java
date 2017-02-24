package com.isoftnet.jobnect.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.isoftnet.jobnect.domain.Job;
import com.isoftnet.jobnect.domain.User;
import com.isoftnet.jobnect.service.JobService;
import com.isoftnet.jobnect.service.UserService;
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
 * REST controller for managing Job.
 */
@RestController
@RequestMapping("/api")
public class JobResource {

    private final Logger log = LoggerFactory.getLogger(JobResource.class);
        
    @Inject
    private JobService jobService;
    
    @Inject
    private UserService userService;

    /**
     * POST  /jobs : Create a new job.
     *
     * @param job the job to create
     * @return the ResponseEntity with status 201 (Created) and with body the new job, or with status 400 (Bad Request) if the job has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/jobs")
    @Timed
    public ResponseEntity<Job> createJob(@Valid @RequestBody Job job) throws URISyntaxException {
        log.debug("REST request to save Job : {}", job);
        if (job.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("job", "idexists", "A new job cannot already have an ID")).body(null);
        }
        
        User user = userService.getUserWithAuthorities();
        job.setCreatedBy(user.getId());
        job.setCreatedOn(ZonedDateTime.now());
        job.setUpdatedOn(ZonedDateTime.now());
        
        Job result = jobService.save(job);
        return ResponseEntity.created(new URI("/api/jobs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("job", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /jobs : Updates an existing job.
     *
     * @param job the job to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated job,
     * or with status 400 (Bad Request) if the job is not valid,
     * or with status 500 (Internal Server Error) if the job couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/jobs")
    @Timed
    public ResponseEntity<Job> updateJob(@Valid @RequestBody Job job) throws URISyntaxException {
        log.debug("REST request to update Job : {}", job);
        if (job.getId() == null) {
            return createJob(job);
        }
        
        job.setUpdatedOn(ZonedDateTime.now());
        Job result = jobService.save(job);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("job", job.getId().toString()))
            .body(result);
    }

    /**
     * GET  /jobs : get all the jobs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of jobs in body
     */
    @GetMapping("/jobs")
    @Timed
    public List<Job> getAllJobs() {
        log.debug("REST request to get all Jobs");
        // return jobService.findAll();
        
        User user = userService.getUserWithAuthorities();
        List<Job> jobs = jobService.findAll();
        for(Job job : jobs) job.setOwner((user.getId().equals(job.getCreatedBy())));
        return jobs;
    }

    /**
     * GET  /jobs/:id : get the "id" job.
     *
     * @param id the id of the job to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the job, or with status 404 (Not Found)
     */
    @GetMapping("/jobs/{id}")
    @Timed
    public ResponseEntity<Job> getJob(@PathVariable Long id) {
        log.debug("REST request to get Job : {}", id);
        Job job = jobService.findOne(id);
        
        if(job == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        
        User user = userService.getUserWithAuthorities();
        job.setOwner((user.getId().equals(job.getCreatedBy())));
        return new ResponseEntity<>(job, HttpStatus.OK);
        
        /*
        return Optional.ofNullable(job)
            .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
         */
    }

    /**
     * DELETE  /jobs/:id : delete the "id" job.
     *
     * @param id the id of the job to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/jobs/{id}")
    @Timed
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        log.debug("REST request to delete Job : {}", id);
        jobService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("job", id.toString())).build();
    }
    
    @DeleteMapping("/jobs")
    @Timed
    public ResponseEntity<Void> deleteJobs(@Valid @RequestBody List<Long> jobIds) {
        log.debug("REST request to delete Jobs : {}", jobIds);
        
        for(Long jobId : jobIds) jobService.delete(jobId);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("job", jobIds.toString())).build();
    }
    
    @PostMapping("/closeJobs")
    @Timed
    public ResponseEntity<Void> closeJobs(@Valid @RequestBody List<Long> jobIds) {
        log.debug("REST request to close Jobs : {}", jobIds);
        
        for(Long jobId : jobIds) jobService.delete(jobId);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("job", jobIds.toString())).build();
    }

}
