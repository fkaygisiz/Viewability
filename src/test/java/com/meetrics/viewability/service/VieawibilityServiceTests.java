package com.meetrics.viewability.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.meetrics.viewability.model.Impression;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VieawibilityServiceTests {

	@Autowired
	private VieawibilityService vieawibilityService;
	
	@SuppressWarnings("serial")
	@Test
	public void shouldGetImpressionWithCorrectPercentageImpressionCountsWhenIdIsGiven() {
		Impression expected = new Impression(1L, new HashMap<String, Integer>() {{put("10%", 123);put("25%", 856);put("50%", 325);put("100%", 98);}});
		Impression actual = vieawibilityService.getImpressionById(1L);
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getData().get("10%"), actual.getData().get("10%"));
		assertEquals(expected.getData().get("25%"), actual.getData().get("25%"));
		assertEquals(expected.getData().get("50%"), actual.getData().get("50%"));
		assertEquals(expected.getData().get("100%"), actual.getData().get("100%"));
	}
	
	@Test
	public void shouldGetAllImpressions() {
		List<Impression> expected = new ArrayList<Impression>() {
			{
				add(new Impression(1L, new HashMap<String, Integer>() {
					{
						put("10%", 123);
						put("25%", 856);
						put("50%", 325);
						put("100%", 98);
					}
				}));
				add(new Impression(2L, new HashMap<String, Integer>() {
					{
						put("10%", 345);
						put("25%", 93);
						put("50%", 237);
						put("100%", 34);
					}
				}));
				add(new Impression(1L, new HashMap<String, Integer>() {
					{
						put("25%", 234);
						put("50%", 13);
					}
				}));

			}
		};
		List<Impression> actual = vieawibilityService.getAllImpressions();
		System.out.println(actual.toString());
		assertEquals(expected.toString(), actual.toString());
	}
}
