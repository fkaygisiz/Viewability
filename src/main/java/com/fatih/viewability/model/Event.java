package com.fatih.viewability.model;

public class Event {
	private Integer view;
	private Integer duration;

	public Event() {
		// For JSON
	}

	public Event(Integer view, Integer duration) {
		super();
		this.view = view;
		this.duration = duration;
	}

	public Integer getView() {
		return view;
	}

	public Integer getDuration() {
		return duration;
	}

}
