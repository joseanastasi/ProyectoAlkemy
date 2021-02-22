package com.alkemy.ot9.repository;

import com.alkemy.ot9.entities.EventEntity;

import feign.Param;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {

	@Override
	List<EventEntity> findAll();

	@Query(value = "SELECT * FROM event order by  abs(now() - start_Date)  DESC LIMIT 3;", nativeQuery = true)
	List<EventEntity> lastThreeEventbyDate();

	@Query(value = "SELECT * FROM event WHERE active = 1 ;", nativeQuery = true)
	List<EventEntity> activeEvent();

	@Override
	<S extends EventEntity> S save(S s);

	@Override
	void deleteById(Long aLong);
	
	@Modifying
    @Query(value = "UPDATE EVENT e SET e.enrolled = e.enrolled + 1 WHERE e.id = :id", nativeQuery = true)
    void enrollBeneficiary (@Param("id") Long id);
}
