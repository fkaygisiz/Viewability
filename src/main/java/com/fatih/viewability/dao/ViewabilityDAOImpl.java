package com.fatih.viewability.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fatih.viewability.dao.DataFileReader.AverageIdImpressionCounter;
import com.fatih.viewability.model.Impression;
import com.fatih.viewability.model.RawEvent;

@Repository
public class ViewabilityDAOImpl implements ViewabilityDAO {

	@Autowired
	private DataFileReader dataFileReader;

	@Override
	public Impression getImpressionById(String id) {
		return dataFileReader.getIdAverageImpressionMap().get(id);
	}

	@Override
	public List<Impression> getAllImpressions() {
		return dataFileReader.getIdAverageImpressions();
	}

	@Override
	public Map<String, Double> getAllImpressionCountOfPercentages() {
		return dataFileReader.getAverageDurationOfPercentages();
	}

	@Override
	public List<RawEvent> getAllEventsById() {
		return dataFileReader.getAllEventsById();
	}

	@Override
	public Map<String, List<AverageIdImpressionCounter>> getViewImpressions() {
		return dataFileReader.getViewImpressions();
	}

}
