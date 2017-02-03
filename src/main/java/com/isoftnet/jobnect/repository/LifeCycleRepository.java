package com.isoftnet.jobnect.repository;

import com.isoftnet.jobnect.domain.LifeCycle;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LifeCycle entity.
 */
@SuppressWarnings("unused")
public interface LifeCycleRepository extends JpaRepository<LifeCycle,Long> {

}
