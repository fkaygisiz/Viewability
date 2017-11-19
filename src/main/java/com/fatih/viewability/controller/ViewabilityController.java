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
	private ViewabilityService vieawibilityService;

	@RequestMapping("/impression/{id}")
	public ResponseEntity<Impression> getImpressionCountsOfPercentagesByAdIdFromPath(@PathVariable String id) {
		return getImpressionCountsOfPercentagesByAd(id);
	}

	@RequestMapping("/impression")
	public ResponseEntity<Impression> getImpressionCountsOfPercentagesByAdIdFromParameter(@RequestParam String id) {
		return getImpressionCountsOfPercentagesByAd(id);
	}

	private ResponseEntity<Impression> getImpressionCountsOfPercentagesByAd(String id) {
		Impression impressionById = vieawibilityService.getImpressionById(id);
		if (impressionById == null) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(impressionById);
	}

	@RequestMapping(value = "/impressions")
	public ResponseEntity<List<Impression>> getAllImpression() {
		return ResponseEntity.ok(vieawibilityService.getAllImpressions());
	}

	@RequestMapping("/impressions/all")
	public ResponseEntity<Map<String, Double>> getAllImpressionCountOfPercentages() {
		return ResponseEntity.ok(vieawibilityService.getAllImpressionCountOfPercentages());
	}

	@RequestMapping(value = "/impressions", params = { "view", "durationHigherThan" })
	public ResponseEntity<List<String>> getImpressionIdsHavingHigherAverageDurationByViewPercentageAndDuration(
			@RequestParam String view, @RequestParam Integer durationHigherThan) {
		return ResponseEntity.ok(vieawibilityService.getImpressionIdsHavingHigherAverageDurationByViewPercentageAndDuration(view,
				durationHigherThan));
	}
	
	@RequestMapping("/raw/ids")
	public ResponseEntity<List<RawEvent>> getAllRawEvents() {
		List<RawEvent> rawEvents = vieawibilityService.getAllRawEvents();
		return ResponseEntity.ok(rawEvents);
	}

}
