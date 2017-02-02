package com.isoftnet.jobnect.repository;

import com.isoftnet.jobnect.domain.JobStatus;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the JobStatus entity.
 */
@SuppressWarnings("unused")
public interface JobStatusRepository extends JpaRepository<JobStatus,Long> {

}
