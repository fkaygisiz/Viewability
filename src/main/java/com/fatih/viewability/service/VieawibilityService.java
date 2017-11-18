package com.fatih.viewability.service;

import java.util.List;
import java.util.Map;

import com.fatih.viewability.model.Impression;

public interface VieawibilityService {

	public Impression getImpressionById(Long id);

	public List<Impression> getAllImpressions();

	public Map<String, Integer> getAllImpressionCountOfPercentages();

	public List<Integer> getImpressionIdsHavingHigherAverageDurationByViewPercentageAndDuration(Integer view,
			Integer durationHigherThan);
}
