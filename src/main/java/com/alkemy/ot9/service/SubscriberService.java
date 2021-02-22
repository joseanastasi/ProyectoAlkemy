package com.alkemy.ot9.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alkemy.ot9.interfaceService.ISubscriberService;
import com.alkemy.ot9.mappers.SubscriberMapper;
import com.alkemy.ot9.models.Subscriber;
import com.alkemy.ot9.repository.SubscriberRepository;

@Service
public class SubscriberService implements ISubscriberService {
	
	private SubscriberRepository subscriberRepository;
	private SubscriberMapper subscriberMapper;
	
	@Autowired
	public SubscriberService(SubscriberRepository subscriberRepository, SubscriberMapper subscriberMapper) {
		super();
		this.subscriberRepository = subscriberRepository;
		this.subscriberMapper = subscriberMapper;
	}

	@Override
	public Long addSubscriber(Subscriber subscriber) {
		return subscriberRepository.save(subscriberMapper.map(subscriber)).getId();
	}

	@Override
	public void deleteSubscriber(String email) {
		subscriberRepository.deleteById(subscriberMapper.map(subscriberRepository.findByEmail(email).get(0)).getId());		
	}

	@Override
	public boolean existEmail(String email) {
		return subscriberRepository.findByEmail(email).size()!=0;
	}
	
	@Override
	public List<Subscriber> getAllSubscribers() {
		return subscriberMapper.map(subscriberRepository.findAll());
	}

}
