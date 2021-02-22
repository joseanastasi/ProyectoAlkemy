package com.alkemy.ot9.mappers;

import com.alkemy.ot9.entities.BeneficiaryEntity;
import com.alkemy.ot9.models.Beneficiary;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class BeneficiaryMapper {

    private ModelMapper modelMapper;

    @Autowired
    public BeneficiaryMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public BeneficiaryEntity map(Beneficiary beneficiary) {
        return modelMapper.map(beneficiary,BeneficiaryEntity.class);
    }

    public Beneficiary map(BeneficiaryEntity beneficiaryEntity) {
        return modelMapper.map(beneficiaryEntity, Beneficiary.class);
    }

    public List<Beneficiary> map(List<BeneficiaryEntity> beneficiaryEntities) {
        List<Beneficiary> beneficiary = new ArrayList<>();
        for (BeneficiaryEntity beneficiaryEntity : beneficiaryEntities) {
            beneficiary.add(map(beneficiaryEntity));
        }
        return beneficiary;
    }

}

