package com.alkemy.ot9.service;

import com.alkemy.ot9.exceptions.EventNotFound;
import com.alkemy.ot9.entities.EventEntity;
import com.alkemy.ot9.interfaceService.IEventService;
import com.alkemy.ot9.mappers.EventMapper;
import com.alkemy.ot9.models.Event;
import com.alkemy.ot9.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;

@Service
@Transactional
public class EventService implements IEventService {

	private EventRepository eventRepository;

	private EventMapper eventMapper;

	@Autowired
	public EventService(EventRepository eventRepository, EventMapper eventMapper) {
		this.eventRepository = eventRepository;
		this.eventMapper = eventMapper;
	}

	public Long createEvent(Event event) {
		EventEntity createdEventEntity = eventRepository.save(eventMapper.map(event));
		return createdEventEntity.getId();
	}

	public List<Event> getAll() {
		return eventMapper.map((List<EventEntity>) eventRepository.findAll());
	}

	public List<Event> lastThreeEventbyDate() {
		return eventMapper.map((List<EventEntity>) eventRepository.lastThreeEventbyDate());

	}

	public Event getEventById(Long id) throws EventNotFound {
		Optional<EventEntity> eventEntity = eventRepository.findById(id);
		if (eventEntity.isEmpty()) {
			throw new EventNotFound();
		}
		return eventMapper.map(eventEntity.get());
	}

	public void deleteEventById(Long id) throws EventNotFound {
		Optional<EventEntity> eventEntity = eventRepository.findById(id);
		if (eventEntity.isEmpty()) {
			throw new EventNotFound();
		}
		eventRepository.deleteById(id);
	}

	@Override
	public Page<EventEntity> getAllEvent(Pageable pageable) {

		return eventRepository.findAll(pageable);
	}
	
	public void enrollBeneficiary(Long id) {
    	
    	eventRepository.enrollBeneficiary(id);    	
    }
}