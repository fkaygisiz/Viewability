package com.fatih.viewability.service;

import java.util.List;
import java.util.Map;

import com.fatih.viewability.model.Impression;
import com.fatih.viewability.model.RawEvent;

public interface ViewabilityService {

	public Impression getImpressionById(String id);

	public List<Impression> getAllImpressions();

	public Map<String, Double> getAllImpressionCountOfPercentages();

	public List<String> getImpressionIdsHavingHigherAverageDurationByViewPercentageAndDuration(String view,
			Integer durationHigherThan);

	public List<RawEvent> getAllRawEvents();
}
