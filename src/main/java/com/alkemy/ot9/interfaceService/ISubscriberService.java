package com.alkemy.ot9.interfaceService;

import java.util.List;

import com.alkemy.ot9.models.Subscriber;

public interface ISubscriberService {
	
	Long addSubscriber(Subscriber subscriber);
	void deleteSubscriber(String email);
	boolean existEmail(String email);
	List<Subscriber> getAllSubscribers();

}
