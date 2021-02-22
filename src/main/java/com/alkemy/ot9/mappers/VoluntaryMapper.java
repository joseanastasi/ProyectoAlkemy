package com.alkemy.ot9.mappers;

import com.alkemy.ot9.entities.VoluntaryEntity;
import com.alkemy.ot9.models.Voluntary;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VoluntaryMapper {

    private ModelMapper modelMapper;

    @Autowired
    public VoluntaryMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public VoluntaryEntity map(Voluntary voluntary) {
        return modelMapper.map(voluntary, VoluntaryEntity.class);
    }

    public Voluntary map(VoluntaryEntity voluntaryEntity) {
        return modelMapper.map(voluntaryEntity, Voluntary.class);
    }

    public List<Voluntary> map(List<VoluntaryEntity> voluntaryEntities) {
        List<Voluntary> voluntaries = new ArrayList<>();
        voluntaryEntities.stream().forEach(voluntaryEntity -> {
            voluntaries.add(map(voluntaryEntity));
        });
        return voluntaries;
    }
}

