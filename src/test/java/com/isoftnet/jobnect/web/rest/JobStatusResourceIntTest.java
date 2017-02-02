package com.isoftnet.jobnect.web.rest;

import com.isoftnet.jobnect.IsoftnetApp;

import com.isoftnet.jobnect.domain.JobStatus;
import com.isoftnet.jobnect.repository.JobStatusRepository;
import com.isoftnet.jobnect.service.JobStatusService;

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
 * Test class for the JobStatusResource REST controller.
 *
 * @see JobStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IsoftnetApp.class)
public class JobStatusResourceIntTest {

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_JOB_ID = 1L;
    private static final Long UPDATED_JOB_ID = 2L;

    @Inject
    private JobStatusRepository jobStatusRepository;

    @Inject
    private JobStatusService jobStatusService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restJobStatusMockMvc;

    private JobStatus jobStatus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JobStatusResource jobStatusResource = new JobStatusResource();
        ReflectionTestUtils.setField(jobStatusResource, "jobStatusService", jobStatusService);
        this.restJobStatusMockMvc = MockMvcBuilders.standaloneSetup(jobStatusResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobStatus createEntity(EntityManager em) {
        JobStatus jobStatus = new JobStatus()
                .comment(DEFAULT_COMMENT)
                .createdOn(DEFAULT_CREATED_ON)
                .updatedOn(DEFAULT_UPDATED_ON)
                .jobId(DEFAULT_JOB_ID);
        return jobStatus;
    }

    @Before
    public void initTest() {
        jobStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createJobStatus() throws Exception {
        int databaseSizeBeforeCreate = jobStatusRepository.findAll().size();

        // Create the JobStatus

        restJobStatusMockMvc.perform(post("/api/job-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobStatus)))
            .andExpect(status().isCreated());

        // Validate the JobStatus in the database
        List<JobStatus> jobStatusList = jobStatusRepository.findAll();
        assertThat(jobStatusList).hasSize(databaseSizeBeforeCreate + 1);
        JobStatus testJobStatus = jobStatusList.get(jobStatusList.size() - 1);
        assertThat(testJobStatus.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testJobStatus.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testJobStatus.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testJobStatus.getJobId()).isEqualTo(DEFAULT_JOB_ID);
    }

    @Test
    @Transactional
    public void createJobStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jobStatusRepository.findAll().size();

        // Create the JobStatus with an existing ID
        JobStatus existingJobStatus = new JobStatus();
        existingJobStatus.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobStatusMockMvc.perform(post("/api/job-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingJobStatus)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<JobStatus> jobStatusList = jobStatusRepository.findAll();
        assertThat(jobStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCommentIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobStatusRepository.findAll().size();
        // set the field null
        jobStatus.setComment(null);

        // Create the JobStatus, which fails.

        restJobStatusMockMvc.perform(post("/api/job-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobStatus)))
            .andExpect(status().isBadRequest());

        List<JobStatus> jobStatusList = jobStatusRepository.findAll();
        assertThat(jobStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkJobIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobStatusRepository.findAll().size();
        // set the field null
        jobStatus.setJobId(null);

        // Create the JobStatus, which fails.

        restJobStatusMockMvc.perform(post("/api/job-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobStatus)))
            .andExpect(status().isBadRequest());

        List<JobStatus> jobStatusList = jobStatusRepository.findAll();
        assertThat(jobStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllJobStatuses() throws Exception {
        // Initialize the database
        jobStatusRepository.saveAndFlush(jobStatus);

        // Get all the jobStatusList
        restJobStatusMockMvc.perform(get("/api/job-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))))
            .andExpect(jsonPath("$.[*].jobId").value(hasItem(DEFAULT_JOB_ID.intValue())));
    }

    @Test
    @Transactional
    public void getJobStatus() throws Exception {
        // Initialize the database
        jobStatusRepository.saveAndFlush(jobStatus);

        // Get the jobStatus
        restJobStatusMockMvc.perform(get("/api/job-statuses/{id}", jobStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(jobStatus.getId().intValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)))
            .andExpect(jsonPath("$.jobId").value(DEFAULT_JOB_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingJobStatus() throws Exception {
        // Get the jobStatus
        restJobStatusMockMvc.perform(get("/api/job-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJobStatus() throws Exception {
        // Initialize the database
        jobStatusService.save(jobStatus);

        int databaseSizeBeforeUpdate = jobStatusRepository.findAll().size();

        // Update the jobStatus
        JobStatus updatedJobStatus = jobStatusRepository.findOne(jobStatus.getId());
        updatedJobStatus
                .comment(UPDATED_COMMENT)
                .createdOn(UPDATED_CREATED_ON)
                .updatedOn(UPDATED_UPDATED_ON)
                .jobId(UPDATED_JOB_ID);

        restJobStatusMockMvc.perform(put("/api/job-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedJobStatus)))
            .andExpect(status().isOk());

        // Validate the JobStatus in the database
        List<JobStatus> jobStatusList = jobStatusRepository.findAll();
        assertThat(jobStatusList).hasSize(databaseSizeBeforeUpdate);
        JobStatus testJobStatus = jobStatusList.get(jobStatusList.size() - 1);
        assertThat(testJobStatus.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testJobStatus.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testJobStatus.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testJobStatus.getJobId()).isEqualTo(UPDATED_JOB_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingJobStatus() throws Exception {
        int databaseSizeBeforeUpdate = jobStatusRepository.findAll().size();

        // Create the JobStatus

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restJobStatusMockMvc.perform(put("/api/job-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobStatus)))
            .andExpect(status().isCreated());

        // Validate the JobStatus in the database
        List<JobStatus> jobStatusList = jobStatusRepository.findAll();
        assertThat(jobStatusList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteJobStatus() throws Exception {
        // Initialize the database
        jobStatusService.save(jobStatus);

        int databaseSizeBeforeDelete = jobStatusRepository.findAll().size();

        // Get the jobStatus
        restJobStatusMockMvc.perform(delete("/api/job-statuses/{id}", jobStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<JobStatus> jobStatusList = jobStatusRepository.findAll();
        assertThat(jobStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
