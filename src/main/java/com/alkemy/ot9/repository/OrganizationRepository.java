package com.alkemy.ot9.repository;

import com.alkemy.ot9.entities.OrganizationEntity;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends CrudRepository <OrganizationEntity, Long> {
	
	@Query(value = "SELECT * FROM ORGANIZATIONS where ACTIVE=1;", nativeQuery = true)
	Optional<OrganizationEntity> getActiveOrganization();
}