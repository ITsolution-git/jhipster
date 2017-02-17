package com.isoftnet.jobnect.repository;

import com.isoftnet.jobnect.domain.JobRating;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the JobRating entity.
 */
@SuppressWarnings("unused")
public interface JobRatingRepository extends JpaRepository<JobRating,Long> {

}
