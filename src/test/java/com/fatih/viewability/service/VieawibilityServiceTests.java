package com.fatih.viewability.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fatih.viewability.model.Impression;
import com.fatih.viewability.model.RawEvent;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VieawibilityServiceTests {

	@Autowired
	private VieawibilityService vieawibilityService;

	@SuppressWarnings("serial")
	@Test
	public void shouldGetImpressionWithCorrectPercentageImpressionCountsWhenIdIsGiven() {
		Impression expected = new Impression(1L, new HashMap<String, Integer>() {
			{
				put("10%", 123);
				put("25%", 856);
				put("50%", 325);
				put("100%", 98);
			}
		});
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

	@Test
	public void shouldGetAllImpressionCountOfPercentages() throws Exception {
		Map<String, Integer> expected = new HashMap<String, Integer>() {
			{
				put("10%", 123);
				put("25%", 856);
				put("50%", 325);
				put("100%", 98);
			}
		};
		Map<String, Integer> actual = vieawibilityService.getAllImpressionCountOfPercentages();

		assertEquals(expected.get("10%"), actual.get("10%"));
		assertEquals(expected.get("25%"), actual.get("25%"));
		assertEquals(expected.get("50%"), actual.get("50%"));
		assertEquals(expected.get("100%"), actual.get("100%"));
	}

	@Test
	public void shouldGetImpressionIdsHavingHigherAverageDurationByViewPercentageAndDuration() {
		Integer view = 25;
		Integer durationHigherThan = 150;
		List<Integer> actual = vieawibilityService
				.getImpressionIdsHavingHigherAverageDurationByViewPercentageAndDuration(view, durationHigherThan);
	}

	@Test
	public void shouldGetEventsSortedById()
			throws JsonParseException, JsonMappingException, IOException, URISyntaxException {

		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
		// builder.featuresToEnable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES);
		ObjectMapper mapper = builder.build();
		ClassLoader classLoader = getClass().getClassLoader();
		String decodedPath = new URI(classLoader.getResource("orderedEventsById.json").toString()).getPath();
		File file = new File(decodedPath);
		final RawEvent[] obj = mapper.readValue(file, RawEvent[].class);
		System.out.println(obj);
	}

}
