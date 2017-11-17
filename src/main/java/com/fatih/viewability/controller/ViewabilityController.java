package com.fatih.viewability.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

	@RequestMapping("/impressions")
	public List<Impression> getAllImpression() {
		return vieawibilityService.getAllImpressions();
	}

}
