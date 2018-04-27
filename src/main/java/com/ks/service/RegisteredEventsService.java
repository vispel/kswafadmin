package com.ks.service;

import com.ks.DAO.RegisteredEventsDAO;
import com.ks.bean.RegisteredEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisteredEventsService {

	@Autowired
	RegisteredEventsDAO registeredEventsDAO;

	public List<RegisteredEvent> getList(){
		return registeredEventsDAO.getList();
	}

	public int addEvent(RegisteredEvent registeredEvent){
		return registeredEventsDAO.addEvent(registeredEvent);
	}


	public boolean deleteEvent(int id){
		return registeredEventsDAO.deleteEvent(id);
	}
}
