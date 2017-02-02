package com.isoftnet.jobnect.repository;

import com.isoftnet.jobnect.domain.JobApplication;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the JobApplication entity.
 */
@SuppressWarnings("unused")
public interface JobApplicationRepository extends JpaRepository<JobApplication,Long> {

}
