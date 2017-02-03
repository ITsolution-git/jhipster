package com.isoftnet.jobnect.web.rest;

import com.isoftnet.jobnect.IsoftnetApp;

import com.isoftnet.jobnect.domain.JobRating;
import com.isoftnet.jobnect.repository.JobRatingRepository;
import com.isoftnet.jobnect.service.JobRatingService;

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
 * Test class for the JobRatingResource REST controller.
 *
 * @see JobRatingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IsoftnetApp.class)
public class JobRatingResourceIntTest {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final Long DEFAULT_JOB_ID = 1L;
    private static final Long UPDATED_JOB_ID = 2L;

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final Integer DEFAULT_RESPONSIVE = 1;
    private static final Integer UPDATED_RESPONSIVE = 2;

    private static final Integer DEFAULT_TRUTHFUL = 1;
    private static final Integer UPDATED_TRUTHFUL = 2;

    private static final String DEFAULT_RELIABLE = "AAAAAAAAAA";
    private static final String UPDATED_RELIABLE = "BBBBBBBBBB";

    private static final Integer DEFAULT_PROFESSIONAL = 1;
    private static final Integer UPDATED_PROFESSIONAL = 2;

    private static final Integer DEFAULT_EFFICIENT = 1;
    private static final Integer UPDATED_EFFICIENT = 2;

    private static final Integer DEFAULT_EFFECTIVE = 1;
    private static final Integer UPDATED_EFFECTIVE = 2;

    private static final Integer DEFAULT_OVERALL = 1;
    private static final Integer UPDATED_OVERALL = 2;

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_INFORMATIVE = 1;
    private static final Integer UPDATED_INFORMATIVE = 2;

    @Inject
    private JobRatingRepository jobRatingRepository;

    @Inject
    private JobRatingService jobRatingService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restJobRatingMockMvc;

    private JobRating jobRating;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JobRatingResource jobRatingResource = new JobRatingResource();
        ReflectionTestUtils.setField(jobRatingResource, "jobRatingService", jobRatingService);
        this.restJobRatingMockMvc = MockMvcBuilders.standaloneSetup(jobRatingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobRating createEntity(EntityManager em) {
        JobRating jobRating = new JobRating()
                .userId(DEFAULT_USER_ID)
                .jobId(DEFAULT_JOB_ID)
                .comment(DEFAULT_COMMENT)
                .responsive(DEFAULT_RESPONSIVE)
                .truthful(DEFAULT_TRUTHFUL)
                .reliable(DEFAULT_RELIABLE)
                .professional(DEFAULT_PROFESSIONAL)
                .efficient(DEFAULT_EFFICIENT)
                .effective(DEFAULT_EFFECTIVE)
                .overall(DEFAULT_OVERALL)
                .createdOn(DEFAULT_CREATED_ON)
                .informative(DEFAULT_INFORMATIVE);
        return jobRating;
    }

    @Before
    public void initTest() {
        jobRating = createEntity(em);
    }

    @Test
    @Transactional
    public void createJobRating() throws Exception {
        int databaseSizeBeforeCreate = jobRatingRepository.findAll().size();

        // Create the JobRating

        restJobRatingMockMvc.perform(post("/api/job-ratings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobRating)))
            .andExpect(status().isCreated());

        // Validate the JobRating in the database
        List<JobRating> jobRatingList = jobRatingRepository.findAll();
        assertThat(jobRatingList).hasSize(databaseSizeBeforeCreate + 1);
        JobRating testJobRating = jobRatingList.get(jobRatingList.size() - 1);
        assertThat(testJobRating.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testJobRating.getJobId()).isEqualTo(DEFAULT_JOB_ID);
        assertThat(testJobRating.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testJobRating.getResponsive()).isEqualTo(DEFAULT_RESPONSIVE);
        assertThat(testJobRating.getTruthful()).isEqualTo(DEFAULT_TRUTHFUL);
        assertThat(testJobRating.getReliable()).isEqualTo(DEFAULT_RELIABLE);
        assertThat(testJobRating.getProfessional()).isEqualTo(DEFAULT_PROFESSIONAL);
        assertThat(testJobRating.getEfficient()).isEqualTo(DEFAULT_EFFICIENT);
        assertThat(testJobRating.getEffective()).isEqualTo(DEFAULT_EFFECTIVE);
        assertThat(testJobRating.getOverall()).isEqualTo(DEFAULT_OVERALL);
        assertThat(testJobRating.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testJobRating.getInformative()).isEqualTo(DEFAULT_INFORMATIVE);
    }

    @Test
    @Transactional
    public void createJobRatingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jobRatingRepository.findAll().size();

        // Create the JobRating with an existing ID
        JobRating existingJobRating = new JobRating();
        existingJobRating.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobRatingMockMvc.perform(post("/api/job-ratings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingJobRating)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<JobRating> jobRatingList = jobRatingRepository.findAll();
        assertThat(jobRatingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobRatingRepository.findAll().size();
        // set the field null
        jobRating.setUserId(null);

        // Create the JobRating, which fails.

        restJobRatingMockMvc.perform(post("/api/job-ratings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobRating)))
            .andExpect(status().isBadRequest());

        List<JobRating> jobRatingList = jobRatingRepository.findAll();
        assertThat(jobRatingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkJobIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobRatingRepository.findAll().size();
        // set the field null
        jobRating.setJobId(null);

        // Create the JobRating, which fails.

        restJobRatingMockMvc.perform(post("/api/job-ratings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobRating)))
            .andExpect(status().isBadRequest());

        List<JobRating> jobRatingList = jobRatingRepository.findAll();
        assertThat(jobRatingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkResponsiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobRatingRepository.findAll().size();
        // set the field null
        jobRating.setResponsive(null);

        // Create the JobRating, which fails.

        restJobRatingMockMvc.perform(post("/api/job-ratings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobRating)))
            .andExpect(status().isBadRequest());

        List<JobRating> jobRatingList = jobRatingRepository.findAll();
        assertThat(jobRatingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTruthfulIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobRatingRepository.findAll().size();
        // set the field null
        jobRating.setTruthful(null);

        // Create the JobRating, which fails.

        restJobRatingMockMvc.perform(post("/api/job-ratings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobRating)))
            .andExpect(status().isBadRequest());

        List<JobRating> jobRatingList = jobRatingRepository.findAll();
        assertThat(jobRatingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReliableIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobRatingRepository.findAll().size();
        // set the field null
        jobRating.setReliable(null);

        // Create the JobRating, which fails.

        restJobRatingMockMvc.perform(post("/api/job-ratings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobRating)))
            .andExpect(status().isBadRequest());

        List<JobRating> jobRatingList = jobRatingRepository.findAll();
        assertThat(jobRatingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProfessionalIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobRatingRepository.findAll().size();
        // set the field null
        jobRating.setProfessional(null);

        // Create the JobRating, which fails.

        restJobRatingMockMvc.perform(post("/api/job-ratings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobRating)))
            .andExpect(status().isBadRequest());

        List<JobRating> jobRatingList = jobRatingRepository.findAll();
        assertThat(jobRatingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEfficientIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobRatingRepository.findAll().size();
        // set the field null
        jobRating.setEfficient(null);

        // Create the JobRating, which fails.

        restJobRatingMockMvc.perform(post("/api/job-ratings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobRating)))
            .andExpect(status().isBadRequest());

        List<JobRating> jobRatingList = jobRatingRepository.findAll();
        assertThat(jobRatingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEffectiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobRatingRepository.findAll().size();
        // set the field null
        jobRating.setEffective(null);

        // Create the JobRating, which fails.

        restJobRatingMockMvc.perform(post("/api/job-ratings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobRating)))
            .andExpect(status().isBadRequest());

        List<JobRating> jobRatingList = jobRatingRepository.findAll();
        assertThat(jobRatingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOverallIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobRatingRepository.findAll().size();
        // set the field null
        jobRating.setOverall(null);

        // Create the JobRating, which fails.

        restJobRatingMockMvc.perform(post("/api/job-ratings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobRating)))
            .andExpect(status().isBadRequest());

        List<JobRating> jobRatingList = jobRatingRepository.findAll();
        assertThat(jobRatingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInformativeIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobRatingRepository.findAll().size();
        // set the field null
        jobRating.setInformative(null);

        // Create the JobRating, which fails.

        restJobRatingMockMvc.perform(post("/api/job-ratings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobRating)))
            .andExpect(status().isBadRequest());

        List<JobRating> jobRatingList = jobRatingRepository.findAll();
        assertThat(jobRatingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllJobRatings() throws Exception {
        // Initialize the database
        jobRatingRepository.saveAndFlush(jobRating);

        // Get all the jobRatingList
        restJobRatingMockMvc.perform(get("/api/job-ratings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobRating.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].jobId").value(hasItem(DEFAULT_JOB_ID.intValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].responsive").value(hasItem(DEFAULT_RESPONSIVE)))
            .andExpect(jsonPath("$.[*].truthful").value(hasItem(DEFAULT_TRUTHFUL)))
            .andExpect(jsonPath("$.[*].reliable").value(hasItem(DEFAULT_RELIABLE.toString())))
            .andExpect(jsonPath("$.[*].professional").value(hasItem(DEFAULT_PROFESSIONAL)))
            .andExpect(jsonPath("$.[*].efficient").value(hasItem(DEFAULT_EFFICIENT)))
            .andExpect(jsonPath("$.[*].effective").value(hasItem(DEFAULT_EFFECTIVE)))
            .andExpect(jsonPath("$.[*].overall").value(hasItem(DEFAULT_OVERALL)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].informative").value(hasItem(DEFAULT_INFORMATIVE)));
    }

    @Test
    @Transactional
    public void getJobRating() throws Exception {
        // Initialize the database
        jobRatingRepository.saveAndFlush(jobRating);

        // Get the jobRating
        restJobRatingMockMvc.perform(get("/api/job-ratings/{id}", jobRating.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(jobRating.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.jobId").value(DEFAULT_JOB_ID.intValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()))
            .andExpect(jsonPath("$.responsive").value(DEFAULT_RESPONSIVE))
            .andExpect(jsonPath("$.truthful").value(DEFAULT_TRUTHFUL))
            .andExpect(jsonPath("$.reliable").value(DEFAULT_RELIABLE.toString()))
            .andExpect(jsonPath("$.professional").value(DEFAULT_PROFESSIONAL))
            .andExpect(jsonPath("$.efficient").value(DEFAULT_EFFICIENT))
            .andExpect(jsonPath("$.effective").value(DEFAULT_EFFECTIVE))
            .andExpect(jsonPath("$.overall").value(DEFAULT_OVERALL))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.informative").value(DEFAULT_INFORMATIVE));
    }

    @Test
    @Transactional
    public void getNonExistingJobRating() throws Exception {
        // Get the jobRating
        restJobRatingMockMvc.perform(get("/api/job-ratings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJobRating() throws Exception {
        // Initialize the database
        jobRatingService.save(jobRating);

        int databaseSizeBeforeUpdate = jobRatingRepository.findAll().size();

        // Update the jobRating
        JobRating updatedJobRating = jobRatingRepository.findOne(jobRating.getId());
        updatedJobRating
                .userId(UPDATED_USER_ID)
                .jobId(UPDATED_JOB_ID)
                .comment(UPDATED_COMMENT)
                .responsive(UPDATED_RESPONSIVE)
                .truthful(UPDATED_TRUTHFUL)
                .reliable(UPDATED_RELIABLE)
                .professional(UPDATED_PROFESSIONAL)
                .efficient(UPDATED_EFFICIENT)
                .effective(UPDATED_EFFECTIVE)
                .overall(UPDATED_OVERALL)
                .createdOn(UPDATED_CREATED_ON)
                .informative(UPDATED_INFORMATIVE);

        restJobRatingMockMvc.perform(put("/api/job-ratings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedJobRating)))
            .andExpect(status().isOk());

        // Validate the JobRating in the database
        List<JobRating> jobRatingList = jobRatingRepository.findAll();
        assertThat(jobRatingList).hasSize(databaseSizeBeforeUpdate);
        JobRating testJobRating = jobRatingList.get(jobRatingList.size() - 1);
        assertThat(testJobRating.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testJobRating.getJobId()).isEqualTo(UPDATED_JOB_ID);
        assertThat(testJobRating.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testJobRating.getResponsive()).isEqualTo(UPDATED_RESPONSIVE);
        assertThat(testJobRating.getTruthful()).isEqualTo(UPDATED_TRUTHFUL);
        assertThat(testJobRating.getReliable()).isEqualTo(UPDATED_RELIABLE);
        assertThat(testJobRating.getProfessional()).isEqualTo(UPDATED_PROFESSIONAL);
        assertThat(testJobRating.getEfficient()).isEqualTo(UPDATED_EFFICIENT);
        assertThat(testJobRating.getEffective()).isEqualTo(UPDATED_EFFECTIVE);
        assertThat(testJobRating.getOverall()).isEqualTo(UPDATED_OVERALL);
        assertThat(testJobRating.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testJobRating.getInformative()).isEqualTo(UPDATED_INFORMATIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingJobRating() throws Exception {
        int databaseSizeBeforeUpdate = jobRatingRepository.findAll().size();

        // Create the JobRating

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restJobRatingMockMvc.perform(put("/api/job-ratings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobRating)))
            .andExpect(status().isCreated());

        // Validate the JobRating in the database
        List<JobRating> jobRatingList = jobRatingRepository.findAll();
        assertThat(jobRatingList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteJobRating() throws Exception {
        // Initialize the database
        jobRatingService.save(jobRating);

        int databaseSizeBeforeDelete = jobRatingRepository.findAll().size();

        // Get the jobRating
        restJobRatingMockMvc.perform(delete("/api/job-ratings/{id}", jobRating.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<JobRating> jobRatingList = jobRatingRepository.findAll();
        assertThat(jobRatingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
