package com.fatih.viewability.service;

import java.util.List;

import com.fatih.viewability.model.Impression;

public interface VieawibilityService {

	public Impression getImpressionById(Long id);

	public List<Impression> getAllImpressions();
}
