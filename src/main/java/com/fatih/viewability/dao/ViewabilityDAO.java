package com.fatih.viewability.dao;

import java.util.List;
import java.util.Map;

import com.fatih.viewability.dao.DataFileReader.AverageIdImpressionCounter;
import com.fatih.viewability.model.Impression;
import com.fatih.viewability.model.RawEvent;

public interface ViewabilityDAO {

	public Impression getImpressionById(String id);

	public List<Impression> getAllImpressions();

	public Map<String, Double> getAllImpressionCountOfPercentages();

	public List<RawEvent> getAllEventsById();

	public Map<String, List<AverageIdImpressionCounter>> getViewImpressions();

}
