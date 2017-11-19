package com.fatih.viewability.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fatih.viewability.dao.DataFileReader.AverageIdImpressionCounter;
import com.fatih.viewability.dao.ViewabilityDAO;
import com.fatih.viewability.model.Impression;
import com.fatih.viewability.model.RawEvent;

@Service
public class ViewabilityServiceImpl implements ViewabilityService {

	@Autowired
	private ViewabilityDAO viewabilityDAO;

	@Override
	public Impression getImpressionById(String id) {
		return viewabilityDAO.getImpressionById(id);

	}

	@Override
	public List<Impression> getAllImpressions() {
		return viewabilityDAO.getAllImpressions();

	}

	@Override
	public Map<String, Double> getAllImpressionCountOfPercentages() {
		return viewabilityDAO.getAllImpressionCountOfPercentages();

	}

	@Override
	public List<String> getImpressionIdsHavingHigherAverageDurationByViewPercentageAndDuration(String view,
			Integer durationHigherThan) {
		Map<String, List<AverageIdImpressionCounter>> viewImpressionsMap = viewabilityDAO.getViewImpressions();
		List<AverageIdImpressionCounter> viewImpressions = viewImpressionsMap.get(view + "%");
		if (CollectionUtils.isEmpty(viewImpressions)) {
			return new ArrayList<>();
		} else {
			return viewImpressions.stream().filter(e -> e.getAverage() > durationHigherThan).map(e -> e.getId())
					.collect(Collectors.toList());
		}
	}

	@Override
	public List<RawEvent> getAllRawEvents() {
		return viewabilityDAO.getAllEventsById();
	}

}
