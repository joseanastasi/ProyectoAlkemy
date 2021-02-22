package com.alkemy.ot9.repository;

import java.util.Optional;

import com.alkemy.ot9.entities.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	Optional<UserEntity> findByEmail(String email);

	UserEntity findByName(String name);
}
