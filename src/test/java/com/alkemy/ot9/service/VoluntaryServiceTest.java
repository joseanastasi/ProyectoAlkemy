package com.alkemy.ot9.service;

import com.alkemy.ot9.entities.VoluntaryEntity;
import com.alkemy.ot9.exceptions.VoluntaryNotFoundException;
import com.alkemy.ot9.mappers.VoluntaryMapper;
import com.alkemy.ot9.models.Voluntary;
import com.alkemy.ot9.repository.VoluntaryRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class VoluntaryServiceTest {


    @Mock
    VoluntaryRepository voluntaryRepository;
    VoluntaryService voluntaryService;
    VoluntaryMapper voluntaryMapper;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        voluntaryMapper = new VoluntaryMapper(new ModelMapper());
        voluntaryService = new VoluntaryService(voluntaryMapper, voluntaryRepository);

    }

    @Test
    public void testFindAllVoluntaries(){

        Long id = 1L;
        String name = "test-name";
        String surname = "test-surname";

        Voluntary voluntary = new Voluntary();
        voluntary.setId(id);
        voluntary.setName(name);
        voluntary.setSurname(surname);

        VoluntaryEntity voluntaryEntity = new VoluntaryEntity();
        voluntaryEntity.setId(id);
        voluntaryEntity.setName(name);
        voluntaryEntity.setSurname(surname);

        List<VoluntaryEntity> voluntaryEntityList = new ArrayList<>();
        voluntaryEntityList.add(voluntaryEntity);

        List<Voluntary> voluntaryList = new ArrayList<>();
        voluntaryList.add(voluntary);

        when(voluntaryRepository.findAll()).thenReturn(voluntaryEntityList);

        List<Voluntary> result = voluntaryService.findAllVoluntaries();

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(voluntary.getId(), result.get(0).getId());
        Assertions.assertEquals(voluntary.getName(), result.get(0).getName());
        Assertions.assertEquals(voluntary.getSurname(), result.get(0).getSurname());
    }

    @Test
    public void testCreateVoluntary(){
        Long id = 1L;

        Voluntary voluntary = new Voluntary();
        voluntary.setId(id);

        VoluntaryEntity voluntaryEntity = new VoluntaryEntity();
        voluntaryEntity.setId(id);

        when(voluntaryRepository.save(any())).thenReturn(voluntaryEntity);

        Long resultId = voluntaryService.createVoluntary(voluntary);

        Assertions.assertEquals(id, resultId);
    }


    @Test
    public void testGetVoluntaryById() throws VoluntaryNotFoundException {
        Long id = 1L;
        String name = "test-name";
        String surname = "test-surname";

        Voluntary voluntary = new Voluntary();
        voluntary.setId(id);
        voluntary.setName(name);
        voluntary.setSurname(surname);

        VoluntaryEntity voluntaryEntity = new VoluntaryEntity();
        voluntaryEntity.setId(id);
        voluntaryEntity.setName(name);
        voluntaryEntity.setSurname(surname);

        when(voluntaryRepository.findById(id)).thenReturn(java.util.Optional.of(voluntaryEntity));

        Voluntary resultVoluntary = voluntaryService.getVoluntaryById(id);

        Assertions.assertEquals(id, resultVoluntary.getId());
        Assertions.assertEquals(name, resultVoluntary.getName());
        Assertions.assertEquals(surname, resultVoluntary.getSurname());
    }


    @Test
    public void testDeleteVoluntaryById() throws VoluntaryNotFoundException {
        Long id = 1L;
        String name = "test-name";
        String surname = "test-surname";

        Voluntary voluntary = new Voluntary();
        voluntary.setId(id);
        voluntary.setName(name);
        voluntary.setSurname(surname);

        VoluntaryEntity voluntaryEntity = new VoluntaryEntity();
        voluntaryEntity.setId(id);
        voluntaryEntity.setName(name);
        voluntaryEntity.setSurname(surname);

        when(voluntaryRepository.findById(id)).thenReturn(java.util.Optional.of(voluntaryEntity));

        voluntaryService.deleteVoluntaryById(1L);
    }
    @Test
    public void testGetVoluntaryByIdException() {
        Long id = 1L;

        when(voluntaryRepository.findById(id)).thenReturn(Optional.empty());

        try {
            voluntaryService.getVoluntaryById(id);
        } catch (VoluntaryNotFoundException e) {
            Assert.assertTrue(true);
            return;
        }
        Assert.fail();
    }
    @Test
    public void testDeleteVoluntaryByIdException() {
        Long id = 1L;

        when(voluntaryRepository.findById(id)).thenReturn(Optional.empty());

        try {
            voluntaryService.deleteVoluntaryById(id);
        } catch (VoluntaryNotFoundException e) {
            Assert.assertTrue(true);
            return;
        }
        Assert.fail();
    }
}
