package com.fatih.viewability.model;

import java.util.HashMap;
import java.util.Map;

public class Impression implements Comparable<Impression> {

	private String id;
	private Map<String, Double> average = new HashMap<>();

	public Impression(String id, Map<String, Double> average) {
		super();
		this.id = id;
		this.average = average;
	}

	public Impression() {
		// This Constructor is added for JSON conversion
	}

	public String getId() {
		return id;
	}

	public Map<String, Double> getAverage() {
		return average;
	}

	@Override
	public String toString() {
		return "Impression [id=" + id + ", average=" + average + "]";
	}

	@Override
	public int compareTo(Impression o) {
		return this.getId().compareTo(o.getId());
	}
}
