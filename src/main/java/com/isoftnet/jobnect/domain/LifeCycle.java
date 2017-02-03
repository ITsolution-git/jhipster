package com.isoftnet.jobnect.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A LifeCycle.
 */
@Entity
@Table(name = "life_cycle")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LifeCycle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 25)
    @Column(name = "event", length = 25, nullable = false)
    private String event;

    @NotNull
    @Column(name = "created_on", nullable = false)
    private ZonedDateTime createdOn;

    @NotNull
    @Column(name = "created_by", nullable = false)
    private Long createdBy;

    @Column(name = "job_id")
    private Long jobId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEvent() {
        return event;
    }

    public LifeCycle event(String event) {
        this.event = event;
        return this;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public ZonedDateTime getCreatedOn() {
        return createdOn;
    }

    public LifeCycle createdOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public LifeCycle createdBy(Long createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getJobId() {
        return jobId;
    }

    public LifeCycle jobId(Long jobId) {
        this.jobId = jobId;
        return this;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LifeCycle lifeCycle = (LifeCycle) o;
        if (lifeCycle.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, lifeCycle.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LifeCycle{" +
            "id=" + id +
            ", event='" + event + "'" +
            ", createdOn='" + createdOn + "'" +
            ", createdBy='" + createdBy + "'" +
            ", jobId='" + jobId + "'" +
            '}';
    }
}
