package com.fatih.viewability.model;

import java.util.ArrayList;
import java.util.List;

public class RawEvent {

	private Long id;
	private List<Event> events = new ArrayList<Event>();

	public Long getId() {
		return id;
	}

	public List<Event> getEvents() {
		return events;
	}

	
}