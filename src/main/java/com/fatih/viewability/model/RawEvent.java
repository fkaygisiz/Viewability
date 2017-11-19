package com.fatih.viewability.model;

import java.util.ArrayList;
import java.util.List;

public class RawEvent implements Comparable<RawEvent> {

	private String id;
	private List<Event> events = new ArrayList<>();

	public RawEvent(String id) {
		super();
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public List<Event> getEvents() {
		return events;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof RawEvent) {
			return this.getId().equals(((RawEvent) o).getId());
		}
		return false;

	}

	@Override
	public int hashCode() {
		return this.getId().hashCode();
	}

	@Override
	public int compareTo(RawEvent o) {
		return this.getId().compareTo(o.getId());
	}

}