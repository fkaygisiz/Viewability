package com.fatih.viewability.model;

import java.util.HashMap;
import java.util.Map;

public class Impression {

	private long id;
	private Map<String, Integer> data = new HashMap<>();

	public Impression(long id, Map<String, Integer> data) {
		super();
		this.id = id;
		this.data = data;
	}

	public Impression() {
		//This Constructor is added for JSON conversion
	}

	public long getId() {
		return id;
	}

	/*
	 * public void setId(long id) { this.id = id; }
	 */

	public Map<String, Integer> getData() {
		return data;
	}

	/*
	 * public void setData(Map<String, Integer> data) { this.data = data; }
	 */

	@Override
	public String toString() {
		return "Impression [id=" + id + ", data=" + data + "]";
	}
}
