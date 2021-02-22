package com.alkemy.ot9.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.alkemy.ot9.entities.VoluntaryEntity;
import com.alkemy.ot9.exceptions.VoluntaryNotFoundException;
import com.alkemy.ot9.interfaceService.IVoluntaryService;
import com.alkemy.ot9.mappers.VoluntaryMapper;
import com.alkemy.ot9.models.Voluntary;
import com.alkemy.ot9.repository.VoluntaryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoluntaryService implements IVoluntaryService {
    @Autowired
    VoluntaryMapper voluntaryMapper;
    @Autowired
    VoluntaryRepository voluntaryRepository;

    @Autowired
    public VoluntaryService(VoluntaryMapper voluntaryMapper, VoluntaryRepository voluntaryRepository) {
        this.voluntaryMapper = voluntaryMapper;
        this.voluntaryRepository = voluntaryRepository;

    }

    public List<Voluntary> findAllVoluntaries() {
        return voluntaryMapper.map((List<VoluntaryEntity>) voluntaryRepository.findAll());
    }

    public Long createVoluntary(Voluntary voluntary) {
        LocalDate date = LocalDate.now();
        voluntary.setDateTime(date);
        VoluntaryEntity createVoluntaryEntity = voluntaryRepository.save(voluntaryMapper.map(voluntary));
        return createVoluntaryEntity.getId();
    }

    public Voluntary getVoluntaryById(Long id) throws VoluntaryNotFoundException {
        Optional<VoluntaryEntity> voluntaryEntity = voluntaryRepository.findById(id);
        if (voluntaryEntity.isEmpty()) {
            throw new VoluntaryNotFoundException();
        }
        return voluntaryMapper.map(voluntaryEntity.get());
    }

    public void deleteVoluntaryById(Long id) throws VoluntaryNotFoundException {
        Optional<VoluntaryEntity> voluntaryEntity = voluntaryRepository.findById(id);
        if (voluntaryEntity.isEmpty()) {
            throw new VoluntaryNotFoundException();
        }
        voluntaryRepository.deleteById(id);
    }

    @Override
    public Page<VoluntaryEntity> getAllVoluntaries(Pageable pageable) {
        return voluntaryRepository.findAll(pageable);
    }

}
