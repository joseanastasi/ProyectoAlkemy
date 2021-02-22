package com.alkemy.ot9.service;

import com.alkemy.ot9.entities.BeneficiaryEntity;
import com.alkemy.ot9.exceptions.BeneficiaryNotFound;
import com.alkemy.ot9.interfaceService.IBeneficiaryService;
import com.alkemy.ot9.mappers.BeneficiaryMapper;
import com.alkemy.ot9.models.Beneficiary;
import com.alkemy.ot9.repository.BeneficiaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BeneficiaryService implements IBeneficiaryService {


    private BeneficiaryRepository beneficiaryRepository;

    private BeneficiaryMapper beneficiaryMapper;

    @Autowired
    public BeneficiaryService(BeneficiaryRepository beneficiaryRepository, BeneficiaryMapper beneficiaryMapper){
        this.beneficiaryRepository = beneficiaryRepository;
        this.beneficiaryMapper = beneficiaryMapper;

    }

    @Override
    public Long createBeneficiary(Beneficiary beneficiary){
        BeneficiaryEntity createdBeneficiaryEntity = beneficiaryRepository.save(beneficiaryMapper.map(beneficiary));
        return createdBeneficiaryEntity.getId();
    }

    @Override
    public List<Beneficiary> getAll(){
        return beneficiaryMapper.map((List<BeneficiaryEntity>) beneficiaryRepository.findAll());
    }
//    @Override
//    public Iterable<Integer> getTotal(){
//         return beneficiaryRepository.getTotalBeneficiaries();
//
//    }

    @Override
    public Beneficiary getBeneficiaryById(Long id) throws BeneficiaryNotFound{
        Optional<BeneficiaryEntity> beneficiaryEntity = beneficiaryRepository.findById(id);
        if(beneficiaryEntity.isEmpty()){
            throw new BeneficiaryNotFound();
        }
        return beneficiaryMapper.map(beneficiaryEntity.get());
    }

    @Override
    public void deleteBeneficiaryById(Long id) throws BeneficiaryNotFound{
        Optional<BeneficiaryEntity> beneficiaryEntity = beneficiaryRepository.findById(id);
        if(beneficiaryEntity.isEmpty()){
            throw new BeneficiaryNotFound();
        }
        beneficiaryRepository.deleteById(id);
    }
}
