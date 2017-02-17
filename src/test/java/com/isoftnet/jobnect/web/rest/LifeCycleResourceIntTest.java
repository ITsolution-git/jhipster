package com.isoftnet.jobnect.web.rest;

import com.isoftnet.jobnect.IsoftnetApp;

import com.isoftnet.jobnect.domain.LifeCycle;
import com.isoftnet.jobnect.repository.LifeCycleRepository;
import com.isoftnet.jobnect.service.LifeCycleService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.isoftnet.jobnect.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the LifeCycleResource REST controller.
 *
 * @see LifeCycleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IsoftnetApp.class)
public class LifeCycleResourceIntTest {

    private static final String DEFAULT_EVENT = "AAAAAAAAAA";
    private static final String UPDATED_EVENT = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final Long DEFAULT_JOB_ID = 1L;
    private static final Long UPDATED_JOB_ID = 2L;

    @Inject
    private LifeCycleRepository lifeCycleRepository;

    @Inject
    private LifeCycleService lifeCycleService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restLifeCycleMockMvc;

    private LifeCycle lifeCycle;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LifeCycleResource lifeCycleResource = new LifeCycleResource();
        ReflectionTestUtils.setField(lifeCycleResource, "lifeCycleService", lifeCycleService);
        this.restLifeCycleMockMvc = MockMvcBuilders.standaloneSetup(lifeCycleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LifeCycle createEntity(EntityManager em) {
        LifeCycle lifeCycle = new LifeCycle()
                .event(DEFAULT_EVENT)
                .createdOn(DEFAULT_CREATED_ON)
                .createdBy(DEFAULT_CREATED_BY)
                .jobId(DEFAULT_JOB_ID);
        return lifeCycle;
    }

    @Before
    public void initTest() {
        lifeCycle = createEntity(em);
    }

    @Test
    @Transactional
    public void createLifeCycle() throws Exception {
        int databaseSizeBeforeCreate = lifeCycleRepository.findAll().size();

        // Create the LifeCycle

        restLifeCycleMockMvc.perform(post("/api/life-cycles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lifeCycle)))
            .andExpect(status().isCreated());

        // Validate the LifeCycle in the database
        List<LifeCycle> lifeCycleList = lifeCycleRepository.findAll();
        assertThat(lifeCycleList).hasSize(databaseSizeBeforeCreate + 1);
        LifeCycle testLifeCycle = lifeCycleList.get(lifeCycleList.size() - 1);
        assertThat(testLifeCycle.getEvent()).isEqualTo(DEFAULT_EVENT);
        assertThat(testLifeCycle.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testLifeCycle.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testLifeCycle.getJobId()).isEqualTo(DEFAULT_JOB_ID);
    }

    @Test
    @Transactional
    public void createLifeCycleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lifeCycleRepository.findAll().size();

        // Create the LifeCycle with an existing ID
        LifeCycle existingLifeCycle = new LifeCycle();
        existingLifeCycle.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLifeCycleMockMvc.perform(post("/api/life-cycles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingLifeCycle)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<LifeCycle> lifeCycleList = lifeCycleRepository.findAll();
        assertThat(lifeCycleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkEventIsRequired() throws Exception {
        int databaseSizeBeforeTest = lifeCycleRepository.findAll().size();
        // set the field null
        lifeCycle.setEvent(null);

        // Create the LifeCycle, which fails.

        restLifeCycleMockMvc.perform(post("/api/life-cycles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lifeCycle)))
            .andExpect(status().isBadRequest());

        List<LifeCycle> lifeCycleList = lifeCycleRepository.findAll();
        assertThat(lifeCycleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedOnIsRequired() throws Exception {
        int databaseSizeBeforeTest = lifeCycleRepository.findAll().size();
        // set the field null
        lifeCycle.setCreatedOn(null);

        // Create the LifeCycle, which fails.

        restLifeCycleMockMvc.perform(post("/api/life-cycles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lifeCycle)))
            .andExpect(status().isBadRequest());

        List<LifeCycle> lifeCycleList = lifeCycleRepository.findAll();
        assertThat(lifeCycleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = lifeCycleRepository.findAll().size();
        // set the field null
        lifeCycle.setCreatedBy(null);

        // Create the LifeCycle, which fails.

        restLifeCycleMockMvc.perform(post("/api/life-cycles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lifeCycle)))
            .andExpect(status().isBadRequest());

        List<LifeCycle> lifeCycleList = lifeCycleRepository.findAll();
        assertThat(lifeCycleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLifeCycles() throws Exception {
        // Initialize the database
        lifeCycleRepository.saveAndFlush(lifeCycle);

        // Get all the lifeCycleList
        restLifeCycleMockMvc.perform(get("/api/life-cycles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lifeCycle.getId().intValue())))
            .andExpect(jsonPath("$.[*].event").value(hasItem(DEFAULT_EVENT.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].jobId").value(hasItem(DEFAULT_JOB_ID.intValue())));
    }

    @Test
    @Transactional
    public void getLifeCycle() throws Exception {
        // Initialize the database
        lifeCycleRepository.saveAndFlush(lifeCycle);

        // Get the lifeCycle
        restLifeCycleMockMvc.perform(get("/api/life-cycles/{id}", lifeCycle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lifeCycle.getId().intValue()))
            .andExpect(jsonPath("$.event").value(DEFAULT_EVENT.toString()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.jobId").value(DEFAULT_JOB_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLifeCycle() throws Exception {
        // Get the lifeCycle
        restLifeCycleMockMvc.perform(get("/api/life-cycles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLifeCycle() throws Exception {
        // Initialize the database
        lifeCycleService.save(lifeCycle);

        int databaseSizeBeforeUpdate = lifeCycleRepository.findAll().size();

        // Update the lifeCycle
        LifeCycle updatedLifeCycle = lifeCycleRepository.findOne(lifeCycle.getId());
        updatedLifeCycle
                .event(UPDATED_EVENT)
                .createdOn(UPDATED_CREATED_ON)
                .createdBy(UPDATED_CREATED_BY)
                .jobId(UPDATED_JOB_ID);

        restLifeCycleMockMvc.perform(put("/api/life-cycles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLifeCycle)))
            .andExpect(status().isOk());

        // Validate the LifeCycle in the database
        List<LifeCycle> lifeCycleList = lifeCycleRepository.findAll();
        assertThat(lifeCycleList).hasSize(databaseSizeBeforeUpdate);
        LifeCycle testLifeCycle = lifeCycleList.get(lifeCycleList.size() - 1);
        assertThat(testLifeCycle.getEvent()).isEqualTo(UPDATED_EVENT);
        assertThat(testLifeCycle.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testLifeCycle.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testLifeCycle.getJobId()).isEqualTo(UPDATED_JOB_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingLifeCycle() throws Exception {
        int databaseSizeBeforeUpdate = lifeCycleRepository.findAll().size();

        // Create the LifeCycle

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLifeCycleMockMvc.perform(put("/api/life-cycles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lifeCycle)))
            .andExpect(status().isCreated());

        // Validate the LifeCycle in the database
        List<LifeCycle> lifeCycleList = lifeCycleRepository.findAll();
        assertThat(lifeCycleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLifeCycle() throws Exception {
        // Initialize the database
        lifeCycleService.save(lifeCycle);

        int databaseSizeBeforeDelete = lifeCycleRepository.findAll().size();

        // Get the lifeCycle
        restLifeCycleMockMvc.perform(delete("/api/life-cycles/{id}", lifeCycle.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LifeCycle> lifeCycleList = lifeCycleRepository.findAll();
        assertThat(lifeCycleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
