package com.alkemy.ot9.interfaceService;

import com.alkemy.ot9.entities.EventEntity;
import com.alkemy.ot9.exceptions.EventNotFound;
import com.alkemy.ot9.models.Event;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IEventService {

	Long createEvent(Event event);

	List<Event> getAll();

	void deleteEventById(Long id) throws EventNotFound;

	Event getEventById(Long id) throws EventNotFound;

	Page<EventEntity> getAllEvent(Pageable pageable);
	
	void enrollBeneficiary(Long id);
}
