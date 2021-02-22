package com.alkemy.ot9.service;
import com.alkemy.ot9.entities.BeneficiaryEntity;
import com.alkemy.ot9.entities.EventEntity;
import com.alkemy.ot9.exceptions.EventNotFound;
import com.alkemy.ot9.mappers.EventMapper;
import com.alkemy.ot9.models.Beneficiary;
import com.alkemy.ot9.models.Event;
import com.alkemy.ot9.repository.EventRepository;
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
public class EventTest {
    @Mock
    EventRepository eventRepository;
    EventMapper eventMapper;
    EventService eventService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        eventMapper = new EventMapper(new ModelMapper());
        eventService = new EventService(eventRepository,eventMapper);
    }

    @Test
    public void testCreateEvent() { 
        Long id = 1L;

        Event event = new Event();
        event.setId(id);

        EventEntity eventEntity = new EventEntity();
        eventEntity.setId(id);

        when(eventRepository.save(any())).thenReturn(eventEntity);
        Long resultId = eventService.createEvent(event);

        Assertions.assertEquals(id, resultId);
    }

    @Test
    public void testFindAllEvent(){

        Long id = 1L;
        String title = "event-test-title";
        String content = "event-test-content";

        List<Beneficiary> beneficiaryList = new ArrayList<>();
        List<BeneficiaryEntity> beneficiaryEntityfList = new ArrayList<>();

        Beneficiary beneficiary1= new Beneficiary();
        beneficiary1.setDni("11223344");
        beneficiary1.setName("Teleforo");
        beneficiary1.setSurname("Roldan");
        beneficiaryList.add(beneficiary1);
        beneficiaryList.add(beneficiary1);

        BeneficiaryEntity beneficiaryEntity1 = new BeneficiaryEntity();
        beneficiaryEntity1.setDni("11223344");
        beneficiaryEntity1.setName("Teleforo");
        beneficiaryEntity1.setSurname("Roldan");
        beneficiaryEntityfList.add(beneficiaryEntity1);
        beneficiaryEntityfList.add(beneficiaryEntity1);

        Event event = new Event();
        event.setId(id);
        event.setTitle(title);
        event.setContent(content);
        event.setBeneficiaries(beneficiaryList);

        EventEntity eventEntity = new EventEntity();
        eventEntity.setId(id);
        eventEntity.setTitle(title);
        eventEntity.setContent(content);

        List<EventEntity> eventEntityArrayList = new ArrayList<>();
        eventEntityArrayList.add(eventEntity);

        List<Event> eventList = new ArrayList<>();
        eventList.add(event);

        when(eventRepository.findAll()).thenReturn(eventEntityArrayList);
        List<Event> result = eventService.getAll();

        Assertions.assertEquals(1, result.size());

        Assertions.assertEquals(event.getId(), result.get(0).getId());

        Assertions.assertEquals(event.getTitle(), result.get(0).getTitle());

        Assertions.assertEquals(event.getContent(), result.get(0).getContent());

        Assertions.assertEquals(event.getBeneficiaries(), result.get(0).getBeneficiaries());
    }
    @Test
    public void testGetEventById() throws EventNotFound {

        Long id = 1L;
        String title = "event-test-title";
        String content = "event-test-content";

        List<Beneficiary> beneficiaryList = new ArrayList<>();
        List<BeneficiaryEntity> beneficiaryEntityfList = new ArrayList<>();

        Beneficiary beneficiary1= new Beneficiary();
        beneficiary1.setDni("11223344");
        beneficiary1.setName("Teleforo");
        beneficiary1.setSurname("Roldan");
        beneficiaryList.add(beneficiary1);
        beneficiaryList.add(beneficiary1);

        BeneficiaryEntity beneficiaryEntity1 = new BeneficiaryEntity();
        beneficiaryEntity1.setDni("11223344");
        beneficiaryEntity1.setName("Teleforo");
        beneficiaryEntity1.setSurname("Roldan");
        beneficiaryEntityfList.add(beneficiaryEntity1);
        beneficiaryEntityfList.add(beneficiaryEntity1);

        Event event = new Event();
        event.setId(id);
        event.setTitle(title);
        event.setContent(content);

        EventEntity eventEntity = new EventEntity();
        eventEntity.setId(id);
        eventEntity.setTitle(title);
        eventEntity.setContent(content);

        when(eventRepository.findById(id)).thenReturn(java.util.Optional.of(eventEntity));
        Event resultEvent = eventService.getEventById(id);

        Assertions.assertEquals(id, resultEvent.getId());

        Assertions.assertEquals(title, resultEvent.getTitle());

        Assertions.assertEquals(content, resultEvent.getContent());

        Assertions.assertEquals(event.getBeneficiaries(), resultEvent.getBeneficiaries());

    }

    @Test
    public void testDeleteEventById() throws EventNotFound {

        Long id = 1L;
        String title = "event-test-title";
        String content = "event-test-content";

        Event event = new Event();
        event.setId(id);
        event.setTitle(title);
        event.setContent(content);

        EventEntity eventEntity = new EventEntity();
        eventEntity.setId(id);
        eventEntity.setTitle(title);
        eventEntity.setContent(content);

        when(eventRepository.findById(id)).thenReturn(java.util.Optional.of(eventEntity));
        eventService.deleteEventById(1L);
    }

    @Test
    public void testGetEventByIdException() {

        Long id = 1L;

        when(eventRepository.findById(id)).thenReturn(Optional.empty());
        try {
            eventService.getEventById(id);
        } catch (EventNotFound e) {

            Assert.assertTrue(true);

            return;
        }

        Assert.fail();

    }

    @Test
    public void testDeleteEventByIdException() {
        Long id = 1L;

        when(eventRepository.findById(id)).thenReturn(Optional.empty());
        try {
            eventService.deleteEventById(id);
        } catch (EventNotFound e) {

            Assert.assertTrue(true);

            return;
        }

        Assert.fail();

    }

}

