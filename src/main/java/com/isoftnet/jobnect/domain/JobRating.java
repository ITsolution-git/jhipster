package com.isoftnet.jobnect.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A JobRating.
 */
@Entity
@Table(name = "job_rating")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class JobRating implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotNull
    @Column(name = "job_id", nullable = false)
    private Long jobId;

    @Size(max = 50)
    @Column(name = "comment", length = 50)
    private String comment;

    @NotNull
    @Column(name = "responsive", nullable = false)
    private Integer responsive;

    @NotNull
    @Column(name = "truthful", nullable = false)
    private Integer truthful;

    @NotNull
    @Column(name = "reliable", nullable = false)
    private String reliable;

    @NotNull
    @Column(name = "professional", nullable = false)
    private Integer professional;

    @NotNull
    @Column(name = "efficient", nullable = false)
    private Integer efficient;

    @NotNull
    @Column(name = "effective", nullable = false)
    private Integer effective;

    @NotNull
    @Column(name = "overall", nullable = false)
    private Integer overall;

    @Column(name = "created_on")
    private ZonedDateTime createdOn;

    @NotNull
    @Column(name = "informative", nullable = false)
    private Integer informative;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public JobRating userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getJobId() {
        return jobId;
    }

    public JobRating jobId(Long jobId) {
        this.jobId = jobId;
        return this;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getComment() {
        return comment;
    }

    public JobRating comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getResponsive() {
        return responsive;
    }

    public JobRating responsive(Integer responsive) {
        this.responsive = responsive;
        return this;
    }

    public void setResponsive(Integer responsive) {
        this.responsive = responsive;
    }

    public Integer getTruthful() {
        return truthful;
    }

    public JobRating truthful(Integer truthful) {
        this.truthful = truthful;
        return this;
    }

    public void setTruthful(Integer truthful) {
        this.truthful = truthful;
    }

    public String getReliable() {
        return reliable;
    }

    public JobRating reliable(String reliable) {
        this.reliable = reliable;
        return this;
    }

    public void setReliable(String reliable) {
        this.reliable = reliable;
    }

    public Integer getProfessional() {
        return professional;
    }

    public JobRating professional(Integer professional) {
        this.professional = professional;
        return this;
    }

    public void setProfessional(Integer professional) {
        this.professional = professional;
    }

    public Integer getEfficient() {
        return efficient;
    }

    public JobRating efficient(Integer efficient) {
        this.efficient = efficient;
        return this;
    }

    public void setEfficient(Integer efficient) {
        this.efficient = efficient;
    }

    public Integer getEffective() {
        return effective;
    }

    public JobRating effective(Integer effective) {
        this.effective = effective;
        return this;
    }

    public void setEffective(Integer effective) {
        this.effective = effective;
    }

    public Integer getOverall() {
        return overall;
    }

    public JobRating overall(Integer overall) {
        this.overall = overall;
        return this;
    }

    public void setOverall(Integer overall) {
        this.overall = overall;
    }

    public ZonedDateTime getCreatedOn() {
        return createdOn;
    }

    public JobRating createdOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Integer getInformative() {
        return informative;
    }

    public JobRating informative(Integer informative) {
        this.informative = informative;
        return this;
    }

    public void setInformative(Integer informative) {
        this.informative = informative;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JobRating jobRating = (JobRating) o;
        if (jobRating.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, jobRating.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "JobRating{" +
            "id=" + id +
            ", userId='" + userId + "'" +
            ", jobId='" + jobId + "'" +
            ", comment='" + comment + "'" +
            ", responsive='" + responsive + "'" +
            ", truthful='" + truthful + "'" +
            ", reliable='" + reliable + "'" +
            ", professional='" + professional + "'" +
            ", efficient='" + efficient + "'" +
            ", effective='" + effective + "'" +
            ", overall='" + overall + "'" +
            ", createdOn='" + createdOn + "'" +
            ", informative='" + informative + "'" +
            '}';
    }
}
