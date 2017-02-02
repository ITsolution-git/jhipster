package com.isoftnet.jobnect.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.isoftnet.jobnect.domain.enumeration.Status;

/**
 * A JobApplication.
 */
@Entity
@Table(name = "job_application")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class JobApplication implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "job_id", nullable = false)
    private Long jobId;

    @Size(max = 255)
    @Column(name = "cover_note", length = 255)
    private String coverNote;

    @Column(name = "resume_name")
    private String resumeName;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "referred_by")
    private String referredBy;

    @Column(name = "created_on")
    private ZonedDateTime createdOn;

    @Column(name = "updated_on")
    private ZonedDateTime updatedOn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getJobId() {
        return jobId;
    }

    public JobApplication jobId(Long jobId) {
        this.jobId = jobId;
        return this;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getCoverNote() {
        return coverNote;
    }

    public JobApplication coverNote(String coverNote) {
        this.coverNote = coverNote;
        return this;
    }

    public void setCoverNote(String coverNote) {
        this.coverNote = coverNote;
    }

    public String getResumeName() {
        return resumeName;
    }

    public JobApplication resumeName(String resumeName) {
        this.resumeName = resumeName;
        return this;
    }

    public void setResumeName(String resumeName) {
        this.resumeName = resumeName;
    }

    public Status getStatus() {
        return status;
    }

    public JobApplication status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public JobApplication userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getReferredBy() {
        return referredBy;
    }

    public JobApplication referredBy(String referredBy) {
        this.referredBy = referredBy;
        return this;
    }

    public void setReferredBy(String referredBy) {
        this.referredBy = referredBy;
    }

    public ZonedDateTime getCreatedOn() {
        return createdOn;
    }

    public JobApplication createdOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public ZonedDateTime getUpdatedOn() {
        return updatedOn;
    }

    public JobApplication updatedOn(ZonedDateTime updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(ZonedDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JobApplication jobApplication = (JobApplication) o;
        if (jobApplication.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, jobApplication.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "JobApplication{" +
            "id=" + id +
            ", jobId='" + jobId + "'" +
            ", coverNote='" + coverNote + "'" +
            ", resumeName='" + resumeName + "'" +
            ", status='" + status + "'" +
            ", userId='" + userId + "'" +
            ", referredBy='" + referredBy + "'" +
            ", createdOn='" + createdOn + "'" +
            ", updatedOn='" + updatedOn + "'" +
            '}';
    }
}
