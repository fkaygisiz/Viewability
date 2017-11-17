package com.meetrics.viewability.model;

import java.util.HashMap;
import java.util.Map;

public class Impression {

	public Impression(long id, Map<String, Integer> data) {
		super();
		this.id = id;
		this.data = data;
	}

	public Impression() {
	}

	private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Map<String, Integer> getData() {
		return data;
	}

	public void setData(Map<String, Integer> data) {
		this.data = data;
	}

	private Map<String, Integer> data = new HashMap<>();

	@Override
	public String toString() {
		return "Impression [id=" + id + ", data=" + data + "]";
	}
}
