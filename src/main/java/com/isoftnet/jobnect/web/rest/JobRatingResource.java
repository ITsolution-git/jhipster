package com.isoftnet.jobnect.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.isoftnet.jobnect.domain.JobRating;
import com.isoftnet.jobnect.service.JobRatingService;
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
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing JobRating.
 */
@RestController
@RequestMapping("/api")
public class JobRatingResource {

    private final Logger log = LoggerFactory.getLogger(JobRatingResource.class);
        
    @Inject
    private JobRatingService jobRatingService;

    /**
     * POST  /job-ratings : Create a new jobRating.
     *
     * @param jobRating the jobRating to create
     * @return the ResponseEntity with status 201 (Created) and with body the new jobRating, or with status 400 (Bad Request) if the jobRating has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/job-ratings")
    @Timed
    public ResponseEntity<JobRating> createJobRating(@Valid @RequestBody JobRating jobRating) throws URISyntaxException {
        log.debug("REST request to save JobRating : {}", jobRating);
        if (jobRating.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("jobRating", "idexists", "A new jobRating cannot already have an ID")).body(null);
        }
        JobRating result = jobRatingService.save(jobRating);
        return ResponseEntity.created(new URI("/api/job-ratings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("jobRating", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /job-ratings : Updates an existing jobRating.
     *
     * @param jobRating the jobRating to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated jobRating,
     * or with status 400 (Bad Request) if the jobRating is not valid,
     * or with status 500 (Internal Server Error) if the jobRating couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/job-ratings")
    @Timed
    public ResponseEntity<JobRating> updateJobRating(@Valid @RequestBody JobRating jobRating) throws URISyntaxException {
        log.debug("REST request to update JobRating : {}", jobRating);
        if (jobRating.getId() == null) {
            return createJobRating(jobRating);
        }
        JobRating result = jobRatingService.save(jobRating);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("jobRating", jobRating.getId().toString()))
            .body(result);
    }

    /**
     * GET  /job-ratings : get all the jobRatings.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of jobRatings in body
     */
    @GetMapping("/job-ratings")
    @Timed
    public List<JobRating> getAllJobRatings() {
        log.debug("REST request to get all JobRatings");
        return jobRatingService.findAll();
    }

    /**
     * GET  /job-ratings/:id : get the "id" jobRating.
     *
     * @param id the id of the jobRating to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the jobRating, or with status 404 (Not Found)
     */
    @GetMapping("/job-ratings/{id}")
    @Timed
    public ResponseEntity<JobRating> getJobRating(@PathVariable Long id) {
        log.debug("REST request to get JobRating : {}", id);
        JobRating jobRating = jobRatingService.findOne(id);
        return Optional.ofNullable(jobRating)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /job-ratings/:id : delete the "id" jobRating.
     *
     * @param id the id of the jobRating to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/job-ratings/{id}")
    @Timed
    public ResponseEntity<Void> deleteJobRating(@PathVariable Long id) {
        log.debug("REST request to delete JobRating : {}", id);
        jobRatingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("jobRating", id.toString())).build();
    }

}
