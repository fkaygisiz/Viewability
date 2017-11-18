package com.fatih.viewability.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fatih.viewability.model.Impression;
import com.fatih.viewability.service.VieawibilityService;

@RestController
@RequestMapping("/viewability")
public class ViewabilityController {

	@Autowired
	private VieawibilityService vieawibilityService;

	@RequestMapping("/impression/{id}")
	public Impression getImpressionCountsOfPercentagesByAdIdFromPath(@PathVariable Long id) {
		return vieawibilityService.getImpressionById(id);
	}

	@RequestMapping("/impression")
	public Impression getImpressionCountsOfPercentagesByAdIdFromParameter(@RequestParam Long id) {
		return vieawibilityService.getImpressionById(id);
	}

	@RequestMapping(value = "/impressions")
	public List<Impression> getAllImpression() {
		return vieawibilityService.getAllImpressions();
	}

	@RequestMapping("/impressions/all")
	public Map<String, Integer> getAllImpressionCountOfPercentages() {
		return vieawibilityService.getAllImpressionCountOfPercentages();
	}

	@RequestMapping(value = "/impressions", params = { "view", "durationHigherThan" })
	public List<Integer> getImpressionIdsHavingHigherAverageDurationByViewPercentageAndDuration(
			@RequestParam Integer view, @RequestParam Integer durationHigherThan) {
		return vieawibilityService.getImpressionIdsHavingHigherAverageDurationByViewPercentageAndDuration(view,
				durationHigherThan);
	}
	
}
