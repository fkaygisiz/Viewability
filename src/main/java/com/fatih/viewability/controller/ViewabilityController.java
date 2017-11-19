package com.fatih.viewability.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fatih.viewability.model.Impression;
import com.fatih.viewability.model.RawEvent;
import com.fatih.viewability.service.ViewabilityService;

@RestController
@RequestMapping("/viewability")
public class ViewabilityController {

	@Autowired
	private ViewabilityService viewabilityService;

	@RequestMapping("/impression/{id}")
	public ResponseEntity<Impression> getImpressionCountsOfPercentagesByAdIdFromPath(@PathVariable String id) {
		return getImpressionCountsOfPercentagesById(id);
	}

	@RequestMapping("/impression")
	public ResponseEntity<Impression> getImpressionCountsOfPercentagesByAdIdFromParameter(@RequestParam String id) {
		return getImpressionCountsOfPercentagesById(id);
	}

	private ResponseEntity<Impression> getImpressionCountsOfPercentagesById(String id) {
		Impression impressionById = viewabilityService.getImpressionById(id);
		if (impressionById == null) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(impressionById);
	}

	@RequestMapping(value = "/impressions")
	public ResponseEntity<List<Impression>> getAllImpressions() {
		return ResponseEntity.ok(viewabilityService.getAllImpressions());
	}

	@RequestMapping("/impressions/all")
	public ResponseEntity<Map<String, Double>> getAllImpressionCountOfPercentages() {
		return ResponseEntity.ok(viewabilityService.getAllImpressionCountOfPercentages());
	}

	@RequestMapping(value = "/impressions", params = { "view", "durationHigherThan" })
	public ResponseEntity<List<String>> getImpressionIdsHavingHigherAverageDurationByViewPercentageAndDuration(
			@RequestParam String view, @RequestParam Integer durationHigherThan) {
		return ResponseEntity.ok(viewabilityService.getImpressionIdsHavingHigherAverageDurationByViewPercentageAndDuration(view,
				durationHigherThan));
	}
	
	@RequestMapping("/raw/ids")
	public ResponseEntity<List<RawEvent>> getAllRawEvents() {
		List<RawEvent> rawEvents = viewabilityService.getAllRawEvents();
		return ResponseEntity.ok(rawEvents);
	}

}
