package com.isoftnet.jobnect.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.isoftnet.jobnect.domain.enumeration.Term;

import com.isoftnet.jobnect.domain.enumeration.JobType;

import com.isoftnet.jobnect.domain.enumeration.Status;

import com.isoftnet.jobnect.domain.enumeration.WorkPermit;

/**
 * A Job.
 */
@Entity
@Table(name = "job")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Job implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 25)
    @Column(name = "title", length = 25, nullable = false)
    private String title;

    @Column(name = "profession")
    private String profession;

    @NotNull
    @Column(name = "duration", nullable = false)
    private Integer duration;

    @Enumerated(EnumType.STRING)
    @Column(name = "term")
    private Term term;

    @NotNull
    @Column(name = "referral_fee", nullable = false)
    private Double referralFee;

    @NotNull
    @Size(max = 50)
    @Column(name = "short_description", length = 50, nullable = false)
    private String shortDescription;

    @NotNull
    @Size(max = 1000)
    @Column(name = "long_description", length = 1000, nullable = false)
    private String longDescription;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private JobType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "work_permit")
    private WorkPermit workPermit;

    @Column(name = "skill")
    private String skill;

    @Column(name = "created_on")
    private ZonedDateTime createdOn;

    @Column(name = "updated_on")
    private ZonedDateTime updatedOn;

    @Column(name = "job_group")
    private String jobGroup;

    @Column(name = "renewable")
    private Boolean renewable;

    @NotNull
    @Column(name = "salary", nullable = false)
    private Double salary;

    @Column(name = "job_url")
    private String jobURL;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Job title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProfession() {
        return profession;
    }

    public Job profession(String profession) {
        this.profession = profession;
        return this;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public Integer getDuration() {
        return duration;
    }

    public Job duration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Term getTerm() {
        return term;
    }

    public Job term(Term term) {
        this.term = term;
        return this;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    public Double getReferralFee() {
        return referralFee;
    }

    public Job referralFee(Double referralFee) {
        this.referralFee = referralFee;
        return this;
    }

    public void setReferralFee(Double referralFee) {
        this.referralFee = referralFee;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public Job shortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
        return this;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public Job longDescription(String longDescription) {
        this.longDescription = longDescription;
        return this;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public JobType getType() {
        return type;
    }

    public Job type(JobType type) {
        this.type = type;
        return this;
    }

    public void setType(JobType type) {
        this.type = type;
    }

    public Status getStatus() {
        return status;
    }

    public Job status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public WorkPermit getWorkPermit() {
        return workPermit;
    }

    public Job workPermit(WorkPermit workPermit) {
        this.workPermit = workPermit;
        return this;
    }

    public void setWorkPermit(WorkPermit workPermit) {
        this.workPermit = workPermit;
    }

    public String getSkill() {
        return skill;
    }

    public Job skill(String skill) {
        this.skill = skill;
        return this;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public ZonedDateTime getCreatedOn() {
        return createdOn;
    }

    public Job createdOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public ZonedDateTime getUpdatedOn() {
        return updatedOn;
    }

    public Job updatedOn(ZonedDateTime updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(ZonedDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public Job jobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
        return this;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public Boolean isRenewable() {
        return renewable;
    }

    public Job renewable(Boolean renewable) {
        this.renewable = renewable;
        return this;
    }

    public void setRenewable(Boolean renewable) {
        this.renewable = renewable;
    }

    public Double getSalary() {
        return salary;
    }

    public Job salary(Double salary) {
        this.salary = salary;
        return this;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getJobURL() {
        return jobURL;
    }

    public Job jobURL(String jobURL) {
        this.jobURL = jobURL;
        return this;
    }

    public void setJobURL(String jobURL) {
        this.jobURL = jobURL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Job job = (Job) o;
        if (job.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, job.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Job{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", profession='" + profession + "'" +
            ", duration='" + duration + "'" +
            ", term='" + term + "'" +
            ", referralFee='" + referralFee + "'" +
            ", shortDescription='" + shortDescription + "'" +
            ", longDescription='" + longDescription + "'" +
            ", type='" + type + "'" +
            ", status='" + status + "'" +
            ", workPermit='" + workPermit + "'" +
            ", skill='" + skill + "'" +
            ", createdOn='" + createdOn + "'" +
            ", updatedOn='" + updatedOn + "'" +
            ", jobGroup='" + jobGroup + "'" +
            ", renewable='" + renewable + "'" +
            ", salary='" + salary + "'" +
            ", jobURL='" + jobURL + "'" +
            '}';
    }
}
