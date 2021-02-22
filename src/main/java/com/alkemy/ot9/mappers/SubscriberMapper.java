package com.alkemy.ot9.mappers;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alkemy.ot9.entities.SubscriberEntity;
import com.alkemy.ot9.models.Subscriber;

@Service
public class SubscriberMapper {
	
	private ModelMapper modelMapper;

	@Autowired
	public SubscriberMapper(ModelMapper modelMapper) {
		super();
		this.modelMapper = modelMapper;
	}
	
	public Subscriber map(SubscriberEntity subscriberEntity) {		
		return modelMapper.map(subscriberEntity, Subscriber.class);
	}
	
	public SubscriberEntity map(Subscriber subscriber) {
		return modelMapper.map(subscriber, SubscriberEntity.class);
	}
	
	public List<Subscriber> map(List<SubscriberEntity> subscribersEntity){
		List<Subscriber> subscribers = new ArrayList<>();		
		subscribersEntity.stream().forEach(s ->{
			subscribers.add(map(s));
		});		
		return subscribers;		
	}
}
