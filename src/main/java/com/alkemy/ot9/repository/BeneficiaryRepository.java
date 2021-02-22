package com.alkemy.ot9.repository;

import com.alkemy.ot9.entities.BeneficiaryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeneficiaryRepository extends CrudRepository<BeneficiaryEntity,Long> {

//    @Query("SELECT COUNT(id) FROM Beneficiary")
//    Iterable<Integer> getTotalBeneficiaries();
}
