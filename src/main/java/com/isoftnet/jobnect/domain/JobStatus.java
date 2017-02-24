package com.isoftnet.jobnect.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A JobStatus.
 */
@Entity
@Table(name = "job_status")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class JobStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "comment", length = 255, nullable = false)
    private String comment;

    @Column(name = "created_on")
    private ZonedDateTime createdOn;

    @Column(name = "updated_on")
    private ZonedDateTime updatedOn;

    @NotNull
    @Column(name = "job_id", nullable = false)
    private Long jobId;

    @Column(name = "created_by")
    private String createdBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public JobStatus comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ZonedDateTime getCreatedOn() {
        return createdOn;
    }

    public JobStatus createdOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public ZonedDateTime getUpdatedOn() {
        return updatedOn;
    }

    public JobStatus updatedOn(ZonedDateTime updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(ZonedDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Long getJobId() {
        return jobId;
    }

    public JobStatus jobId(Long jobId) {
        this.jobId = jobId;
        return this;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public JobStatus createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JobStatus jobStatus = (JobStatus) o;
        if (jobStatus.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, jobStatus.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "JobStatus{" +
            "id=" + id +
            ", comment='" + comment + "'" +
            ", createdOn='" + createdOn + "'" +
            ", updatedOn='" + updatedOn + "'" +
            ", jobId='" + jobId + "'" +
            ", createdBy='" + createdBy + "'" +
            '}';
    }
}
