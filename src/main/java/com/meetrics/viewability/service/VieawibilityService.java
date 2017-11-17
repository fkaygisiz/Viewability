package com.meetrics.viewability.service;

import java.util.List;

import com.meetrics.viewability.model.Impression;

public interface VieawibilityService {

	public Impression getImpressionById(Long id);

	public List<Impression> getAllImpressions();
}
