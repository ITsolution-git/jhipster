package com.isoftnet.jobnect.web.rest;

import com.isoftnet.jobnect.IsoftnetApp;

import com.isoftnet.jobnect.domain.Job;
import com.isoftnet.jobnect.repository.JobRepository;
import com.isoftnet.jobnect.service.JobService;

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

import com.isoftnet.jobnect.domain.enumeration.Term;
import com.isoftnet.jobnect.domain.enumeration.JobType;
import com.isoftnet.jobnect.domain.enumeration.Status;
import com.isoftnet.jobnect.domain.enumeration.WorkPermit;
/**
 * Test class for the JobResource REST controller.
 *
 * @see JobResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IsoftnetApp.class)
public class JobResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_PROFESSION = "AAAAAAAAAA";
    private static final String UPDATED_PROFESSION = "BBBBBBBBBB";

    private static final Integer DEFAULT_DURATION = 1;
    private static final Integer UPDATED_DURATION = 2;

    private static final Term DEFAULT_TERM = Term.HOUR;
    private static final Term UPDATED_TERM = Term.DAY;

    private static final Double DEFAULT_REFERRAL_FEE = 1D;
    private static final Double UPDATED_REFERRAL_FEE = 2D;

    private static final String DEFAULT_SHORT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_LONG_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_LONG_DESCRIPTION = "BBBBBBBBBB";

    private static final JobType DEFAULT_TYPE = JobType.CONTRACT;
    private static final JobType UPDATED_TYPE = JobType.FULL_TIME;

    private static final Status DEFAULT_STATUS = Status.OPEN;
    private static final Status UPDATED_STATUS = Status.CLOSED;

    private static final WorkPermit DEFAULT_WORK_PERMIT = WorkPermit.GREENCARD;
    private static final WorkPermit UPDATED_WORK_PERMIT = WorkPermit.CITIZEN;

    private static final String DEFAULT_SKILL = "AAAAAAAAAA";
    private static final String UPDATED_SKILL = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_JOB_GROUP = "AAAAAAAAAA";
    private static final String UPDATED_JOB_GROUP = "BBBBBBBBBB";

    private static final Boolean DEFAULT_RENEWABLE = false;
    private static final Boolean UPDATED_RENEWABLE = true;

    private static final Double DEFAULT_SALARY = 1D;
    private static final Double UPDATED_SALARY = 2D;

    private static final String DEFAULT_JOB_URL = "AAAAAAAAAA";
    private static final String UPDATED_JOB_URL = "BBBBBBBBBB";

    @Inject
    private JobRepository jobRepository;

    @Inject
    private JobService jobService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restJobMockMvc;

    private Job job;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JobResource jobResource = new JobResource();
        ReflectionTestUtils.setField(jobResource, "jobService", jobService);
        this.restJobMockMvc = MockMvcBuilders.standaloneSetup(jobResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Job createEntity(EntityManager em) {
        Job job = new Job()
                .title(DEFAULT_TITLE)
                .profession(DEFAULT_PROFESSION)
                .duration(DEFAULT_DURATION)
                .term(DEFAULT_TERM)
                .referralFee(DEFAULT_REFERRAL_FEE)
                .shortDescription(DEFAULT_SHORT_DESCRIPTION)
                .longDescription(DEFAULT_LONG_DESCRIPTION)
                .type(DEFAULT_TYPE)
                .status(DEFAULT_STATUS)
                .workPermit(DEFAULT_WORK_PERMIT)
                .skill(DEFAULT_SKILL)
                .createdOn(DEFAULT_CREATED_ON)
                .updatedOn(DEFAULT_UPDATED_ON)
                .jobGroup(DEFAULT_JOB_GROUP)
                .renewable(DEFAULT_RENEWABLE)
                .salary(DEFAULT_SALARY)
                .jobURL(DEFAULT_JOB_URL);
        return job;
    }

    @Before
    public void initTest() {
        job = createEntity(em);
    }

    @Test
    @Transactional
    public void createJob() throws Exception {
        int databaseSizeBeforeCreate = jobRepository.findAll().size();

        // Create the Job

        restJobMockMvc.perform(post("/api/jobs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(job)))
            .andExpect(status().isCreated());

        // Validate the Job in the database
        List<Job> jobList = jobRepository.findAll();
        assertThat(jobList).hasSize(databaseSizeBeforeCreate + 1);
        Job testJob = jobList.get(jobList.size() - 1);
        assertThat(testJob.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testJob.getProfession()).isEqualTo(DEFAULT_PROFESSION);
        assertThat(testJob.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testJob.getTerm()).isEqualTo(DEFAULT_TERM);
        assertThat(testJob.getReferralFee()).isEqualTo(DEFAULT_REFERRAL_FEE);
        assertThat(testJob.getShortDescription()).isEqualTo(DEFAULT_SHORT_DESCRIPTION);
        assertThat(testJob.getLongDescription()).isEqualTo(DEFAULT_LONG_DESCRIPTION);
        assertThat(testJob.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testJob.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testJob.getWorkPermit()).isEqualTo(DEFAULT_WORK_PERMIT);
        assertThat(testJob.getSkill()).isEqualTo(DEFAULT_SKILL);
        assertThat(testJob.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testJob.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testJob.getJobGroup()).isEqualTo(DEFAULT_JOB_GROUP);
        assertThat(testJob.isRenewable()).isEqualTo(DEFAULT_RENEWABLE);
        assertThat(testJob.getSalary()).isEqualTo(DEFAULT_SALARY);
        assertThat(testJob.getJobURL()).isEqualTo(DEFAULT_JOB_URL);
    }

    @Test
    @Transactional
    public void createJobWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jobRepository.findAll().size();

        // Create the Job with an existing ID
        Job existingJob = new Job();
        existingJob.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobMockMvc.perform(post("/api/jobs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingJob)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Job> jobList = jobRepository.findAll();
        assertThat(jobList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobRepository.findAll().size();
        // set the field null
        job.setTitle(null);

        // Create the Job, which fails.

        restJobMockMvc.perform(post("/api/jobs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(job)))
            .andExpect(status().isBadRequest());

        List<Job> jobList = jobRepository.findAll();
        assertThat(jobList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDurationIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobRepository.findAll().size();
        // set the field null
        job.setDuration(null);

        // Create the Job, which fails.

        restJobMockMvc.perform(post("/api/jobs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(job)))
            .andExpect(status().isBadRequest());

        List<Job> jobList = jobRepository.findAll();
        assertThat(jobList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReferralFeeIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobRepository.findAll().size();
        // set the field null
        job.setReferralFee(null);

        // Create the Job, which fails.

        restJobMockMvc.perform(post("/api/jobs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(job)))
            .andExpect(status().isBadRequest());

        List<Job> jobList = jobRepository.findAll();
        assertThat(jobList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkShortDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobRepository.findAll().size();
        // set the field null
        job.setShortDescription(null);

        // Create the Job, which fails.

        restJobMockMvc.perform(post("/api/jobs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(job)))
            .andExpect(status().isBadRequest());

        List<Job> jobList = jobRepository.findAll();
        assertThat(jobList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLongDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobRepository.findAll().size();
        // set the field null
        job.setLongDescription(null);

        // Create the Job, which fails.

        restJobMockMvc.perform(post("/api/jobs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(job)))
            .andExpect(status().isBadRequest());

        List<Job> jobList = jobRepository.findAll();
        assertThat(jobList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSalaryIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobRepository.findAll().size();
        // set the field null
        job.setSalary(null);

        // Create the Job, which fails.

        restJobMockMvc.perform(post("/api/jobs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(job)))
            .andExpect(status().isBadRequest());

        List<Job> jobList = jobRepository.findAll();
        assertThat(jobList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllJobs() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList
        restJobMockMvc.perform(get("/api/jobs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(job.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].profession").value(hasItem(DEFAULT_PROFESSION.toString())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
            .andExpect(jsonPath("$.[*].term").value(hasItem(DEFAULT_TERM.toString())))
            .andExpect(jsonPath("$.[*].referralFee").value(hasItem(DEFAULT_REFERRAL_FEE.doubleValue())))
            .andExpect(jsonPath("$.[*].shortDescription").value(hasItem(DEFAULT_SHORT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].longDescription").value(hasItem(DEFAULT_LONG_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].workPermit").value(hasItem(DEFAULT_WORK_PERMIT.toString())))
            .andExpect(jsonPath("$.[*].skill").value(hasItem(DEFAULT_SKILL.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))))
            .andExpect(jsonPath("$.[*].jobGroup").value(hasItem(DEFAULT_JOB_GROUP.toString())))
            .andExpect(jsonPath("$.[*].renewable").value(hasItem(DEFAULT_RENEWABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].salary").value(hasItem(DEFAULT_SALARY.doubleValue())))
            .andExpect(jsonPath("$.[*].jobURL").value(hasItem(DEFAULT_JOB_URL.toString())));
    }

    @Test
    @Transactional
    public void getJob() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get the job
        restJobMockMvc.perform(get("/api/jobs/{id}", job.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(job.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.profession").value(DEFAULT_PROFESSION.toString()))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION))
            .andExpect(jsonPath("$.term").value(DEFAULT_TERM.toString()))
            .andExpect(jsonPath("$.referralFee").value(DEFAULT_REFERRAL_FEE.doubleValue()))
            .andExpect(jsonPath("$.shortDescription").value(DEFAULT_SHORT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.longDescription").value(DEFAULT_LONG_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.workPermit").value(DEFAULT_WORK_PERMIT.toString()))
            .andExpect(jsonPath("$.skill").value(DEFAULT_SKILL.toString()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)))
            .andExpect(jsonPath("$.jobGroup").value(DEFAULT_JOB_GROUP.toString()))
            .andExpect(jsonPath("$.renewable").value(DEFAULT_RENEWABLE.booleanValue()))
            .andExpect(jsonPath("$.salary").value(DEFAULT_SALARY.doubleValue()))
            .andExpect(jsonPath("$.jobURL").value(DEFAULT_JOB_URL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingJob() throws Exception {
        // Get the job
        restJobMockMvc.perform(get("/api/jobs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJob() throws Exception {
        // Initialize the database
        jobService.save(job);

        int databaseSizeBeforeUpdate = jobRepository.findAll().size();

        // Update the job
        Job updatedJob = jobRepository.findOne(job.getId());
        updatedJob
                .title(UPDATED_TITLE)
                .profession(UPDATED_PROFESSION)
                .duration(UPDATED_DURATION)
                .term(UPDATED_TERM)
                .referralFee(UPDATED_REFERRAL_FEE)
                .shortDescription(UPDATED_SHORT_DESCRIPTION)
                .longDescription(UPDATED_LONG_DESCRIPTION)
                .type(UPDATED_TYPE)
                .status(UPDATED_STATUS)
                .workPermit(UPDATED_WORK_PERMIT)
                .skill(UPDATED_SKILL)
                .createdOn(UPDATED_CREATED_ON)
                .updatedOn(UPDATED_UPDATED_ON)
                .jobGroup(UPDATED_JOB_GROUP)
                .renewable(UPDATED_RENEWABLE)
                .salary(UPDATED_SALARY)
                .jobURL(UPDATED_JOB_URL);

        restJobMockMvc.perform(put("/api/jobs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedJob)))
            .andExpect(status().isOk());

        // Validate the Job in the database
        List<Job> jobList = jobRepository.findAll();
        assertThat(jobList).hasSize(databaseSizeBeforeUpdate);
        Job testJob = jobList.get(jobList.size() - 1);
        assertThat(testJob.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testJob.getProfession()).isEqualTo(UPDATED_PROFESSION);
        assertThat(testJob.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testJob.getTerm()).isEqualTo(UPDATED_TERM);
        assertThat(testJob.getReferralFee()).isEqualTo(UPDATED_REFERRAL_FEE);
        assertThat(testJob.getShortDescription()).isEqualTo(UPDATED_SHORT_DESCRIPTION);
        assertThat(testJob.getLongDescription()).isEqualTo(UPDATED_LONG_DESCRIPTION);
        assertThat(testJob.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testJob.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testJob.getWorkPermit()).isEqualTo(UPDATED_WORK_PERMIT);
        assertThat(testJob.getSkill()).isEqualTo(UPDATED_SKILL);
        assertThat(testJob.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testJob.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testJob.getJobGroup()).isEqualTo(UPDATED_JOB_GROUP);
        assertThat(testJob.isRenewable()).isEqualTo(UPDATED_RENEWABLE);
        assertThat(testJob.getSalary()).isEqualTo(UPDATED_SALARY);
        assertThat(testJob.getJobURL()).isEqualTo(UPDATED_JOB_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingJob() throws Exception {
        int databaseSizeBeforeUpdate = jobRepository.findAll().size();

        // Create the Job

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restJobMockMvc.perform(put("/api/jobs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(job)))
            .andExpect(status().isCreated());

        // Validate the Job in the database
        List<Job> jobList = jobRepository.findAll();
        assertThat(jobList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteJob() throws Exception {
        // Initialize the database
        jobService.save(job);

        int databaseSizeBeforeDelete = jobRepository.findAll().size();

        // Get the job
        restJobMockMvc.perform(delete("/api/jobs/{id}", job.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Job> jobList = jobRepository.findAll();
        assertThat(jobList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
