package com.fatih.viewability.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import com.fatih.viewability.dao.DataFileReader;
import com.fatih.viewability.dao.DataFileReader.AverageIdImpressionCounter;
import com.fatih.viewability.dao.ViewabilityDAO;
import com.fatih.viewability.model.Impression;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ViewabilityServiceTests {

	@Autowired
	private ViewabilityService vieawibilityService;

	@MockBean
	private ViewabilityDAO viewabilityDAO;

	@SuppressWarnings("serial")
	@Test
	public void shouldGetImpressionWithCorrectPercentageImpressionCountsWhenIdIsGiven() {
		when(viewabilityDAO.getImpressionById(anyString()))
				.thenReturn(new Impression("1", new HashMap<String, Double>() {
					{
						put("10%", 123.0);
						put("100%", 98.0);
					}
				}));
		Impression actual = vieawibilityService.getImpressionById("1");
		assertEquals("1", actual.getId());
		assertEquals(Double.valueOf(123.0), actual.getAverage().get("10%"));
		assertEquals(Double.valueOf(98.0), actual.getAverage().get("100%"));
	}

	@Test
	public void shouldGetAllImpressions() {
		when(viewabilityDAO.getAllImpressions()).thenReturn(new ArrayList<Impression>() {
			{
				add(new Impression("1", new HashMap<String, Double>() {
					{
						put("10%", 123.0);
						put("50%", 325.0);
					}
				}));
				add(new Impression("2", new HashMap<String, Double>() {
					{
						put("10%", 345.0);
						put("50%", 237.0);
					}
				}));

			}
		});
		List<Impression> actual = vieawibilityService.getAllImpressions();
		System.out.println(actual.toString());
		assertEquals(2, actual.size());
		assertEquals("1", actual.get(0).getId());
		assertEquals(Double.valueOf(345.0), actual.get(1).getAverage().get("10%"));
	}

	@Test
	public void shouldGetAllImpressionCountOfPercentages() throws Exception {
		when(viewabilityDAO.getAllImpressionCountOfPercentages()).thenReturn(new HashMap<String, Double>() {
			{
				put("10%", 123.0);
				put("25%", 856.0);
				put("50%", 325.0);
				put("100%", 98.0);
			}
		});
		Map<String, Double> actual = vieawibilityService.getAllImpressionCountOfPercentages();

		assertEquals(Double.valueOf(123.0), actual.get("10%"));
		assertEquals(Double.valueOf(856.0), actual.get("25%"));
		assertEquals(Double.valueOf(325.0), actual.get("50%"));
		assertEquals(Double.valueOf(98.0), actual.get("100%"));
	}

	@Test
	public void shouldGetImpressionIdsHavingHigherAverageDurationByViewPercentageAndDuration() {
		Map<String, List<AverageIdImpressionCounter>> value = new HashMap<>();
		DataFileReader dataFileReader = new DataFileReader();
		List<AverageIdImpressionCounter> list10 = new ArrayList<AverageIdImpressionCounter>() {
			{
				add(dataFileReader.new AverageIdImpressionCounter(4, "1", 300));// average 75.0
				add(dataFileReader.new AverageIdImpressionCounter(4, "2", 500));// average 125.0
			}
		};
		List<AverageIdImpressionCounter> list25 = new ArrayList<AverageIdImpressionCounter>() {
			{
				add(dataFileReader.new AverageIdImpressionCounter(4, "3", 400));// average 100
				add(dataFileReader.new AverageIdImpressionCounter(3, "4", 1200));// average 400
			}
		};
		value.put("10%", list10);
		value.put("25%", list25);
		when(viewabilityDAO.getViewImpressions()).thenReturn(value);
		List<String> actual = vieawibilityService
				.getImpressionIdsHavingHigherAverageDurationByViewPercentageAndDuration("10", 150);
		assertTrue(CollectionUtils.isEmpty(actual));
		List<String> actual1 = vieawibilityService
				.getImpressionIdsHavingHigherAverageDurationByViewPercentageAndDuration("10", 100);
		assertTrue(actual1.size() == 1 && "2".equals(actual1.get(0)));

		List<String> actual2 = vieawibilityService
				.getImpressionIdsHavingHigherAverageDurationByViewPercentageAndDuration("25", 100);
		assertTrue(actual2.size() == 1 && "4".equals(actual2.get(0)));

		List<String> actual3 = vieawibilityService
				.getImpressionIdsHavingHigherAverageDurationByViewPercentageAndDuration("25", 90);
		assertTrue(actual3.size() == 2 && actual3.contains("4"));

	}

}
