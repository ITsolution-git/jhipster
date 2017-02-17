package com.isoftnet.jobnect.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.isoftnet.jobnect.domain.Reference;
import com.isoftnet.jobnect.service.ReferenceService;
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
 * REST controller for managing Reference.
 */
@RestController
@RequestMapping("/api")
public class ReferenceResource {

    private final Logger log = LoggerFactory.getLogger(ReferenceResource.class);
        
    @Inject
    private ReferenceService referenceService;

    /**
     * POST  /references : Create a new reference.
     *
     * @param reference the reference to create
     * @return the ResponseEntity with status 201 (Created) and with body the new reference, or with status 400 (Bad Request) if the reference has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/references")
    @Timed
    public ResponseEntity<Reference> createReference(@Valid @RequestBody Reference reference) throws URISyntaxException {
        log.debug("REST request to save Reference : {}", reference);
        if (reference.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("reference", "idexists", "A new reference cannot already have an ID")).body(null);
        }
        Reference result = referenceService.save(reference);
        return ResponseEntity.created(new URI("/api/references/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("reference", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /references : Updates an existing reference.
     *
     * @param reference the reference to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated reference,
     * or with status 400 (Bad Request) if the reference is not valid,
     * or with status 500 (Internal Server Error) if the reference couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/references")
    @Timed
    public ResponseEntity<Reference> updateReference(@Valid @RequestBody Reference reference) throws URISyntaxException {
        log.debug("REST request to update Reference : {}", reference);
        if (reference.getId() == null) {
            return createReference(reference);
        }
        Reference result = referenceService.save(reference);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("reference", reference.getId().toString()))
            .body(result);
    }

    /**
     * GET  /references : get all the references.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of references in body
     */
    @GetMapping("/references")
    @Timed
    public List<Reference> getAllReferences() {
        log.debug("REST request to get all References");
        return referenceService.findAll();
    }

    /**
     * GET  /references/:id : get the "id" reference.
     *
     * @param id the id of the reference to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the reference, or with status 404 (Not Found)
     */
    @GetMapping("/references/{id}")
    @Timed
    public ResponseEntity<Reference> getReference(@PathVariable Long id) {
        log.debug("REST request to get Reference : {}", id);
        Reference reference = referenceService.findOne(id);
        return Optional.ofNullable(reference)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /references/:id : delete the "id" reference.
     *
     * @param id the id of the reference to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/references/{id}")
    @Timed
    public ResponseEntity<Void> deleteReference(@PathVariable Long id) {
        log.debug("REST request to delete Reference : {}", id);
        referenceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("reference", id.toString())).build();
    }

}
