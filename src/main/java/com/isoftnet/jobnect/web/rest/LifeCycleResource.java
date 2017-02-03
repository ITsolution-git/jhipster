package com.isoftnet.jobnect.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.isoftnet.jobnect.domain.LifeCycle;
import com.isoftnet.jobnect.service.LifeCycleService;
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
 * REST controller for managing LifeCycle.
 */
@RestController
@RequestMapping("/api")
public class LifeCycleResource {

    private final Logger log = LoggerFactory.getLogger(LifeCycleResource.class);
        
    @Inject
    private LifeCycleService lifeCycleService;

    /**
     * POST  /life-cycles : Create a new lifeCycle.
     *
     * @param lifeCycle the lifeCycle to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lifeCycle, or with status 400 (Bad Request) if the lifeCycle has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/life-cycles")
    @Timed
    public ResponseEntity<LifeCycle> createLifeCycle(@Valid @RequestBody LifeCycle lifeCycle) throws URISyntaxException {
        log.debug("REST request to save LifeCycle : {}", lifeCycle);
        if (lifeCycle.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("lifeCycle", "idexists", "A new lifeCycle cannot already have an ID")).body(null);
        }
        LifeCycle result = lifeCycleService.save(lifeCycle);
        return ResponseEntity.created(new URI("/api/life-cycles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("lifeCycle", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /life-cycles : Updates an existing lifeCycle.
     *
     * @param lifeCycle the lifeCycle to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lifeCycle,
     * or with status 400 (Bad Request) if the lifeCycle is not valid,
     * or with status 500 (Internal Server Error) if the lifeCycle couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/life-cycles")
    @Timed
    public ResponseEntity<LifeCycle> updateLifeCycle(@Valid @RequestBody LifeCycle lifeCycle) throws URISyntaxException {
        log.debug("REST request to update LifeCycle : {}", lifeCycle);
        if (lifeCycle.getId() == null) {
            return createLifeCycle(lifeCycle);
        }
        LifeCycle result = lifeCycleService.save(lifeCycle);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("lifeCycle", lifeCycle.getId().toString()))
            .body(result);
    }

    /**
     * GET  /life-cycles : get all the lifeCycles.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of lifeCycles in body
     */
    @GetMapping("/life-cycles")
    @Timed
    public List<LifeCycle> getAllLifeCycles() {
        log.debug("REST request to get all LifeCycles");
        return lifeCycleService.findAll();
    }

    /**
     * GET  /life-cycles/:id : get the "id" lifeCycle.
     *
     * @param id the id of the lifeCycle to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lifeCycle, or with status 404 (Not Found)
     */
    @GetMapping("/life-cycles/{id}")
    @Timed
    public ResponseEntity<LifeCycle> getLifeCycle(@PathVariable Long id) {
        log.debug("REST request to get LifeCycle : {}", id);
        LifeCycle lifeCycle = lifeCycleService.findOne(id);
        return Optional.ofNullable(lifeCycle)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /life-cycles/:id : delete the "id" lifeCycle.
     *
     * @param id the id of the lifeCycle to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/life-cycles/{id}")
    @Timed
    public ResponseEntity<Void> deleteLifeCycle(@PathVariable Long id) {
        log.debug("REST request to delete LifeCycle : {}", id);
        lifeCycleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("lifeCycle", id.toString())).build();
    }

}
