package com.alkemy.ot9.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alkemy.ot9.entities.SubscriberEntity;

public interface SubscriberRepository extends JpaRepository<SubscriberEntity, Long> {
	
	List<SubscriberEntity> findByEmail(String email);

}