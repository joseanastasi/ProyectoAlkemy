package com.alkemy.ot9.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.alkemy.ot9.entities.ContributorEntity;

@Repository
public interface ContributorRepository extends JpaRepository<ContributorEntity, Long> {

	@Query(value = "SELECT * FROM contributor order by contributor_id DESC LIMIT 10;", nativeQuery = true)
	List<ContributorEntity> lastTen();

	List<ContributorEntity> findByEmail(String email);
}