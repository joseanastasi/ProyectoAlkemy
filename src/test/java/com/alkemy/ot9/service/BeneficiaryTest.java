package com.alkemy.ot9.service;

import com.alkemy.ot9.entities.BeneficiaryEntity;
import com.alkemy.ot9.entities.EventEntity;
import com.alkemy.ot9.exceptions.BeneficiaryNotFound;
import com.alkemy.ot9.mappers.BeneficiaryMapper;
import com.alkemy.ot9.models.Beneficiary;
import com.alkemy.ot9.models.Event;
import com.alkemy.ot9.repository.BeneficiaryRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BeneficiaryTest {
    @Mock
    BeneficiaryRepository beneficiaryRepository;
    BeneficiaryMapper beneficiaryMapper;
    BeneficiaryService beneficiaryService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        beneficiaryMapper = new BeneficiaryMapper(new ModelMapper());
        beneficiaryService = new BeneficiaryService(beneficiaryRepository,beneficiaryMapper);
    }

    @Test
    public void testCreateBeneficiary() {

        Long id = 1L;

        Beneficiary beneficiary = new Beneficiary();
        beneficiary.setId(id);

        BeneficiaryEntity beneficiaryEntity = new BeneficiaryEntity();
        beneficiaryEntity.setId(id);

        when(beneficiaryRepository.save(any())).thenReturn(beneficiaryEntity);
        Long resultId = beneficiaryService.createBeneficiary(beneficiary);

        Assertions.assertEquals(id, resultId);
    }

    @Test
    public void testFindAllBeneficiaries(){

        Long id = 1L;
        String name = "beneficiary-test-title";
        String surname = "beneficiary-test-content";

        List<Event> eventList = new ArrayList<>();
        List<EventEntity> eventEntityList = new ArrayList<>();

        Event event1 = new Event();
        event1.setTitle("fight against crime");
        event1.setContent("event-test-content");
        event1.setActive(true);
        Event event2 = new Event();
        event2.setTitle("fight against crime 2");
        event2.setContent("event-test-content");
        event2.setActive(true);
        Event event3 = new Event();
        event3.setTitle("fight against crime 3");
        event3.setContent("event-test-content");
        event3.setActive(false);

        EventEntity eventEntity1 = new EventEntity();
        eventEntity1.setTitle("fight against crime");
        eventEntity1.setContent("event-test-content");
        eventEntity1.setActive(true);
        EventEntity eventEntity2 = new EventEntity();
        eventEntity2.setTitle("fight against crime 2");
        eventEntity2.setContent("event-test-content");
        eventEntity2.setActive(true);
        EventEntity eventEntity3 = new EventEntity();
        eventEntity3.setTitle("fight against crime 3");
        eventEntity3.setContent("event-test-content");
        eventEntity3.setActive(false);

        Beneficiary beneficiary = new Beneficiary();
        beneficiary.setId(id);
        beneficiary.setName(name);
        beneficiary.setSurname(surname);
        beneficiary.addEvent(event1);
        beneficiary.addEvent(event2);
        beneficiary.addEvent(event3);

        BeneficiaryEntity beneficiaryEntity = new BeneficiaryEntity();
        beneficiaryEntity.setId(id);
        beneficiaryEntity.setName(name);
        beneficiaryEntity.setSurname(surname);
        beneficiaryEntity.addEvent(eventEntity1);
        beneficiaryEntity.addEvent(eventEntity2);
        beneficiaryEntity.addEvent(eventEntity3);

        List<BeneficiaryEntity> beneficiaryEntityList = new ArrayList<>();
        beneficiaryEntityList.add(beneficiaryEntity);

        List<Beneficiary> beneficiaryList = new ArrayList<>();
        beneficiaryList.add(beneficiary);

        int totalEventAddbeneficiary = beneficiary.getEvent().size();
        int totalEventAddbeneficiaryEntity = beneficiaryEntity.getEvent().size();

        when(beneficiaryRepository.findAll()).thenReturn(beneficiaryEntityList);
        List<Beneficiary> result = beneficiaryService.getAll();

        Assertions.assertEquals(1, result.size());

        Assertions.assertEquals(beneficiary.getId(), result.get(0).getId());

        Assertions.assertEquals(beneficiary.getName(), result.get(0).getName());

        Assertions.assertEquals(beneficiary.getSurname(), result.get(0).getSurname());
        // 2 Active 1 Inactive
        Assertions.assertEquals(2, totalEventAddbeneficiary);

        Assertions.assertEquals(2, totalEventAddbeneficiaryEntity);


    }
    @Test
    public void testGetBeneficiaryById() throws BeneficiaryNotFound {

        Long id = 1L;
        String name = "beneficiary-test-title";
        String surname = "beneficiary-test-content";

        Event event1 = new Event();
        event1.setTitle("fight against crime");
        event1.setContent("event-test-content");
        event1.setActive(true);
        Event event2 = new Event();
        event2.setTitle("fight against crime 2");
        event2.setContent("event-test-content");
        event2.setActive(true);
        Event event3 = new Event();
        event3.setTitle("fight against crime 3");
        event3.setContent("event-test-content");
        event3.setActive(false);

        EventEntity eventEntity1 = new EventEntity();
        eventEntity1.setTitle("fight against crime");
        eventEntity1.setContent("event-test-content");
        eventEntity1.setActive(true);
        EventEntity eventEntity2 = new EventEntity();
        eventEntity2.setTitle("fight against crime 2");
        eventEntity2.setContent("event-test-content");
        eventEntity2.setActive(true);
        EventEntity eventEntity3 = new EventEntity();
        eventEntity3.setTitle("fight against crime 3");
        eventEntity3.setContent("event-test-content");
        eventEntity3.setActive(false);

        Beneficiary beneficiary = new Beneficiary();
        beneficiary.setId(id);
        beneficiary.setName(name);
        beneficiary.setSurname(surname);
        beneficiary.addEvent(event1);
        beneficiary.addEvent(event2);
        beneficiary.addEvent(event3);

        BeneficiaryEntity beneficiaryEntity = new BeneficiaryEntity();
        beneficiaryEntity.setId(id);
        beneficiaryEntity.setName(name);
        beneficiaryEntity.setSurname(surname);
        beneficiaryEntity.addEvent(eventEntity1);
        beneficiaryEntity.addEvent(eventEntity2);
        beneficiaryEntity.addEvent(eventEntity3);

        int totalEventAddbeneficiary = beneficiary.getEvent().size();
        int totalEventAddbeneficiaryEntity = beneficiaryEntity.getEvent().size();

        when(beneficiaryRepository.findById(id)).thenReturn(java.util.Optional.of(beneficiaryEntity));

        Beneficiary resultBeneficiary = beneficiaryService.getBeneficiaryById(id);

        Assertions.assertEquals(id, resultBeneficiary.getId());

        Assertions.assertEquals(name, resultBeneficiary.getName());

        Assertions.assertEquals(surname, resultBeneficiary.getSurname());
        // 2 Active 1 Inactive
        Assertions.assertEquals(2, totalEventAddbeneficiary);

        Assertions.assertEquals(2, totalEventAddbeneficiaryEntity);

    }

    @Test
    public void testDeleteBeneficiaryById() throws BeneficiaryNotFound {

        Long id = 1L;
        String name = "beneficiary-test-title";
        String surname = "beneficiary-test-content";

        Beneficiary beneficiary = new Beneficiary();
        beneficiary.setId(id);
        beneficiary.setName(name);
        beneficiary.setSurname(surname);

        BeneficiaryEntity beneficiaryEntity = new BeneficiaryEntity();
        beneficiaryEntity.setId(id);
        beneficiaryEntity.setName(name);
        beneficiaryEntity.setSurname(surname);

        when(beneficiaryRepository.findById(id)).thenReturn(java.util.Optional.of(beneficiaryEntity));
        beneficiaryService.deleteBeneficiaryById(1L);
    }

    @Test
    public void testGetBeneficiaryByIdException() {

        Long id = 1L;

        when(beneficiaryRepository.findById(id)).thenReturn(Optional.empty());
        try {
            beneficiaryService.getBeneficiaryById(id);
        } catch (BeneficiaryNotFound e) {

            Assert.assertTrue(true);

            return;
        }

        Assert.fail();

    }

    @Test
    public void testDeleteBeneficiaryByIdException() {
        Long id = 1L;

        when(beneficiaryRepository.findById(id)).thenReturn(Optional.empty());
        try {
            beneficiaryService.deleteBeneficiaryById(id);
        } catch (BeneficiaryNotFound e) {

            Assert.assertTrue(true);

            return;
        }

        Assert.fail();

    }

}