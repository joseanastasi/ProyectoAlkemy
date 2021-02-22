package com.alkemy.ot9.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alkemy.ot9.entities.NewsEntity;

@Repository
public interface INewsRepository extends JpaRepository<NewsEntity, Long> {

	@Query(value = "SELECT * FROM news WHERE enabled = :enabled ORDER BY date DESC", nativeQuery = true)
	Page<NewsEntity> listNewsToEnable(@Param("enabled") boolean enabled, Pageable pageable);
}
