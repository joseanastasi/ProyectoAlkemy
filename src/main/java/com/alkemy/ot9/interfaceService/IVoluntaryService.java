package com.alkemy.ot9.interfaceService;

import com.alkemy.ot9.entities.VoluntaryEntity;
import com.alkemy.ot9.exceptions.VoluntaryNotFoundException;
import com.alkemy.ot9.models.Voluntary;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IVoluntaryService {

    Long createVoluntary(Voluntary voluntary);

    List<Voluntary> findAllVoluntaries();

    Voluntary getVoluntaryById(Long id) throws VoluntaryNotFoundException;

    void deleteVoluntaryById(Long id) throws VoluntaryNotFoundException;

    Page<VoluntaryEntity> getAllVoluntaries(Pageable pageable);

}
