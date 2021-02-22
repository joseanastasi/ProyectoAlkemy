package com.alkemy.ot9.mappers;


import com.alkemy.ot9.entities.EventEntity;
import com.alkemy.ot9.models.Event;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventMapper {

    private ModelMapper modelMapper;
    private DateTimeFormatter dateTimeFormatter;

    @Autowired
    public EventMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public EventEntity map(Event event) {
        return modelMapper.map(event,EventEntity.class);
    }

    public Event map(EventEntity eventEntity) {
        return modelMapper.map(eventEntity, Event.class);
    }

    public List<Event> map(List<EventEntity> eventEntities) {
        List<Event> event = new ArrayList<>();
        for (EventEntity eventEntity : eventEntities) {
            event.add(map(eventEntity));
        }
        return event;
    }

}
