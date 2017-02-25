package com.isoftnet.jobnect.web.rest;

import com.isoftnet.jobnect.IsoftnetApp;

import com.isoftnet.jobnect.domain.JobApplication;
import com.isoftnet.jobnect.repository.JobApplicationRepository;
import com.isoftnet.jobnect.service.JobApplicationService;

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

import com.isoftnet.jobnect.domain.enumeration.Status;
/**
 * Test class for the JobApplicationResource REST controller.
 *
 * @see JobApplicationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IsoftnetApp.class)
public class JobApplicationResourceIntTest {

    private static final Long DEFAULT_JOB_ID = 1L;
    private static final Long UPDATED_JOB_ID = 2L;

    private static final String DEFAULT_COVER_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_COVER_NOTE = "BBBBBBBBBB";

    private static final String DEFAULT_RESUME_NAME = "AAAAAAAAAA";
    private static final String UPDATED_RESUME_NAME = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.OPEN;
    private static final Status UPDATED_STATUS = Status.CLOSED;

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final String DEFAULT_REFERRED_BY = "AAAAAAAAAA";
    private static final String UPDATED_REFERRED_BY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Inject
    private JobApplicationRepository jobApplicationRepository;

    @Inject
    private JobApplicationService jobApplicationService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restJobApplicationMockMvc;

    private JobApplication jobApplication;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JobApplicationResource jobApplicationResource = new JobApplicationResource();
        ReflectionTestUtils.setField(jobApplicationResource, "jobApplicationService", jobApplicationService);
        this.restJobApplicationMockMvc = MockMvcBuilders.standaloneSetup(jobApplicationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobApplication createEntity(EntityManager em) {
        JobApplication jobApplication = new JobApplication()
                .jobId(DEFAULT_JOB_ID)
                .coverNote(DEFAULT_COVER_NOTE)
                .resumeName(DEFAULT_RESUME_NAME)
                .status(DEFAULT_STATUS)
                .userId(DEFAULT_USER_ID)
                .referredBy(DEFAULT_REFERRED_BY)
                .createdOn(DEFAULT_CREATED_ON)
                .updatedOn(DEFAULT_UPDATED_ON);
        return jobApplication;
    }

    @Before
    public void initTest() {
        jobApplication = createEntity(em);
    }

    @Test
    @Transactional
    public void createJobApplication() throws Exception {
        int databaseSizeBeforeCreate = jobApplicationRepository.findAll().size();

        // Create the JobApplication

        restJobApplicationMockMvc.perform(post("/api/job-applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobApplication)))
            .andExpect(status().isCreated());

        // Validate the JobApplication in the database
        List<JobApplication> jobApplicationList = jobApplicationRepository.findAll();
        assertThat(jobApplicationList).hasSize(databaseSizeBeforeCreate + 1);
        JobApplication testJobApplication = jobApplicationList.get(jobApplicationList.size() - 1);
        assertThat(testJobApplication.getJobId()).isEqualTo(DEFAULT_JOB_ID);
        assertThat(testJobApplication.getCoverNote()).isEqualTo(DEFAULT_COVER_NOTE);
        assertThat(testJobApplication.getResumeName()).isEqualTo(DEFAULT_RESUME_NAME);
        assertThat(testJobApplication.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testJobApplication.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testJobApplication.getReferredBy()).isEqualTo(DEFAULT_REFERRED_BY);
        //assertThat(testJobApplication.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        //assertThat(testJobApplication.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    public void createJobApplicationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jobApplicationRepository.findAll().size();

        // Create the JobApplication with an existing ID
        JobApplication existingJobApplication = new JobApplication();
        existingJobApplication.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobApplicationMockMvc.perform(post("/api/job-applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingJobApplication)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<JobApplication> jobApplicationList = jobApplicationRepository.findAll();
        assertThat(jobApplicationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkJobIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobApplicationRepository.findAll().size();
        // set the field null
        jobApplication.setJobId(null);

        // Create the JobApplication, which fails.

        restJobApplicationMockMvc.perform(post("/api/job-applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobApplication)))
            .andExpect(status().isBadRequest());

        List<JobApplication> jobApplicationList = jobApplicationRepository.findAll();
        assertThat(jobApplicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobApplicationRepository.findAll().size();
        // set the field null
        jobApplication.setUserId(null);

        // Create the JobApplication, which fails.

        restJobApplicationMockMvc.perform(post("/api/job-applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobApplication)))
            .andExpect(status().isBadRequest());

        List<JobApplication> jobApplicationList = jobApplicationRepository.findAll();
        assertThat(jobApplicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllJobApplications() throws Exception {
        // Initialize the database
        jobApplicationRepository.saveAndFlush(jobApplication);

        // Get all the jobApplicationList
        restJobApplicationMockMvc.perform(get("/api/job-applications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobApplication.getId().intValue())))
            .andExpect(jsonPath("$.[*].jobId").value(hasItem(DEFAULT_JOB_ID.intValue())))
            .andExpect(jsonPath("$.[*].coverNote").value(hasItem(DEFAULT_COVER_NOTE.toString())))
            .andExpect(jsonPath("$.[*].resumeName").value(hasItem(DEFAULT_RESUME_NAME.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].referredBy").value(hasItem(DEFAULT_REFERRED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))));
    }

    @Test
    @Transactional
    public void getJobApplication() throws Exception {
        // Initialize the database
        jobApplicationRepository.saveAndFlush(jobApplication);

        // Get the jobApplication
        restJobApplicationMockMvc.perform(get("/api/job-applications/{id}", jobApplication.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(jobApplication.getId().intValue()))
            .andExpect(jsonPath("$.jobId").value(DEFAULT_JOB_ID.intValue()))
            .andExpect(jsonPath("$.coverNote").value(DEFAULT_COVER_NOTE.toString()))
            .andExpect(jsonPath("$.resumeName").value(DEFAULT_RESUME_NAME.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.referredBy").value(DEFAULT_REFERRED_BY.toString()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)));
    }

    @Test
    @Transactional
    public void getNonExistingJobApplication() throws Exception {
        // Get the jobApplication
        restJobApplicationMockMvc.perform(get("/api/job-applications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJobApplication() throws Exception {
        // Initialize the database
        jobApplicationService.save(jobApplication);

        int databaseSizeBeforeUpdate = jobApplicationRepository.findAll().size();

        // Update the jobApplication
        JobApplication updatedJobApplication = jobApplicationRepository.findOne(jobApplication.getId());
        updatedJobApplication
                .jobId(UPDATED_JOB_ID)
                .coverNote(UPDATED_COVER_NOTE)
                .resumeName(UPDATED_RESUME_NAME)
                .status(UPDATED_STATUS)
                .userId(UPDATED_USER_ID)
                .referredBy(UPDATED_REFERRED_BY)
                .createdOn(UPDATED_CREATED_ON)
                .updatedOn(UPDATED_UPDATED_ON);

        restJobApplicationMockMvc.perform(put("/api/job-applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedJobApplication)))
            .andExpect(status().isOk());

        // Validate the JobApplication in the database
        List<JobApplication> jobApplicationList = jobApplicationRepository.findAll();
        assertThat(jobApplicationList).hasSize(databaseSizeBeforeUpdate);
        JobApplication testJobApplication = jobApplicationList.get(jobApplicationList.size() - 1);
        assertThat(testJobApplication.getJobId()).isEqualTo(UPDATED_JOB_ID);
        assertThat(testJobApplication.getCoverNote()).isEqualTo(UPDATED_COVER_NOTE);
        assertThat(testJobApplication.getResumeName()).isEqualTo(UPDATED_RESUME_NAME);
        assertThat(testJobApplication.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testJobApplication.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testJobApplication.getReferredBy()).isEqualTo(UPDATED_REFERRED_BY);
        //assertThat(testJobApplication.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        //assertThat(testJobApplication.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void updateNonExistingJobApplication() throws Exception {
        int databaseSizeBeforeUpdate = jobApplicationRepository.findAll().size();

        // Create the JobApplication

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restJobApplicationMockMvc.perform(put("/api/job-applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobApplication)))
            .andExpect(status().isCreated());

        // Validate the JobApplication in the database
        List<JobApplication> jobApplicationList = jobApplicationRepository.findAll();
        assertThat(jobApplicationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteJobApplication() throws Exception {
        // Initialize the database
        jobApplicationService.save(jobApplication);

        int databaseSizeBeforeDelete = jobApplicationRepository.findAll().size();

        // Get the jobApplication
        restJobApplicationMockMvc.perform(delete("/api/job-applications/{id}", jobApplication.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<JobApplication> jobApplicationList = jobApplicationRepository.findAll();
        assertThat(jobApplicationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
