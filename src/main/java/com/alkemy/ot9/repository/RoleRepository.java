package com.alkemy.ot9.repository;

import java.util.Optional;

import com.alkemy.ot9.entities.RoleEntity;
import com.alkemy.ot9.models.RoleEnum;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(RoleEnum name);
}