package com.isoftnet.jobnect.service.impl;

import com.isoftnet.jobnect.service.LifeCycleService;
import com.isoftnet.jobnect.domain.LifeCycle;
import com.isoftnet.jobnect.repository.LifeCycleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing LifeCycle.
 */
@Service
@Transactional
public class LifeCycleServiceImpl implements LifeCycleService{

    private final Logger log = LoggerFactory.getLogger(LifeCycleServiceImpl.class);
    
    @Inject
    private LifeCycleRepository lifeCycleRepository;

    /**
     * Save a lifeCycle.
     *
     * @param lifeCycle the entity to save
     * @return the persisted entity
     */
    public LifeCycle save(LifeCycle lifeCycle) {
        log.debug("Request to save LifeCycle : {}", lifeCycle);
        LifeCycle result = lifeCycleRepository.save(lifeCycle);
        return result;
    }

    /**
     *  Get all the lifeCycles.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<LifeCycle> findAll() {
        log.debug("Request to get all LifeCycles");
        List<LifeCycle> result = lifeCycleRepository.findAll();

        return result;
    }

    /**
     *  Get one lifeCycle by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public LifeCycle findOne(Long id) {
        log.debug("Request to get LifeCycle : {}", id);
        LifeCycle lifeCycle = lifeCycleRepository.findOne(id);
        return lifeCycle;
    }

    /**
     *  Delete the  lifeCycle by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete LifeCycle : {}", id);
        lifeCycleRepository.delete(id);
    }
}
