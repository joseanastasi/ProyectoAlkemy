package com.alkemy.ot9.repository;

import com.alkemy.ot9.entities.VoluntaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoluntaryRepository extends JpaRepository<VoluntaryEntity, Long> {
}
