package com.fatih.viewability.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.collection.IsArrayContaining.hasItemInArray;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ViewabilityControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void shouldGetImpressionCountsOfPercentagesByAdIdFromPath() throws Exception {
		this.mockMvc.perform(get("/viewability/impression/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.10%%").value(123))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.25%%").value(856))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.50%%").value(325))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.100%%").value(98));
	}

	@Test
	public void shouldGetImpressionCountsOfPercentagesByAdIdFromParameter() throws Exception {
		this.mockMvc.perform(get("/viewability/impression?id=1")).andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.10%%").value(123))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.25%%").value(856))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.50%%").value(325))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.100%%").value(98));
	}

	@Test
	public void shouldGetAllImpression() throws Exception {
		this.mockMvc.perform(get("/viewability/impressions")).andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].data.10%%").value(123))
				.andExpect(MockMvcResultMatchers.jsonPath("$.[1].data.25%%").value(93))
				.andExpect(MockMvcResultMatchers.jsonPath("$.[2].data.50%%").value(13));
	}

	@Test
	public void shouldGetAllImpressionCountOfPercentages() throws Exception {
		this.mockMvc.perform(get("/viewability/impressions/all")).andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.10%%").value(123))
				.andExpect(MockMvcResultMatchers.jsonPath("$.25%%").value(856))
				.andExpect(MockMvcResultMatchers.jsonPath("$.50%%").value(325))
				.andExpect(MockMvcResultMatchers.jsonPath("$.100%%").value(98));
	}

	@Test
	public void shouldGetImpressionIdsHavingHigherAverageDurationByViewPercentageAndDuration() throws Exception {
		List<Integer> expected = new ArrayList<Integer>() {
			{
				add(7);
				add(14);
				add(18);
			}
		};
		this.mockMvc.perform(get("/viewability/impressions?view=25&durationHigherThan=150")).andDo(print())
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.containsInAnyOrder(expected.toArray())));

	}

}
