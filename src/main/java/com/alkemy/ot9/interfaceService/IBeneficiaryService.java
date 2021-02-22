package com.alkemy.ot9.interfaceService;

import com.alkemy.ot9.exceptions.BeneficiaryNotFound;
import com.alkemy.ot9.models.Beneficiary;

import java.util.List;

public interface IBeneficiaryService {

    Long createBeneficiary(Beneficiary beneficiary);
    List<Beneficiary> getAll();
//    Iterable<Integer> getTotal();
    Beneficiary getBeneficiaryById(Long id) throws BeneficiaryNotFound;
    void deleteBeneficiaryById(Long id) throws BeneficiaryNotFound;
}
