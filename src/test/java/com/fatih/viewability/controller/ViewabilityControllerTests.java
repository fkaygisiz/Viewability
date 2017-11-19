package com.fatih.viewability.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
	public void shouldGetImpressionCountsOfPercentagesByAdIdFromPathTest1() throws Exception {
		this.mockMvc.perform(get("/viewability/impression?id=XX-CVG")).andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value("XX-CVG"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.average.25%%").value(930.0))
				.andExpect(MockMvcResultMatchers.jsonPath("$.average.100%%").value(2800));
	}

	@Test
	public void shouldGetNoContentErrorFromGetImpressionCountsOfPercentagesByAdIdFromPathWhenIdIsNotFound()
			throws Exception {
		this.mockMvc.perform(get("/viewability/impression/12313132")).andDo(print()).andExpect(status().isNoContent());
	}

	@Test
	public void shouldGetImpressionCountsOfPercentagesByAdIdFromPathTest2() throws Exception {
		this.mockMvc.perform(get("/viewability/impression?id=HFC-PKJ")).andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value("HFC-PKJ"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.average.10%%").value(2050.0))
				.andExpect(MockMvcResultMatchers.jsonPath("$.average.100%%").value(1240));
	}

	@Test
	public void shouldGetImpressionCountsOfPercentagesByAdIdFromParameter() throws Exception {
		this.mockMvc.perform(get("/viewability/impression/XX-CVG")).andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value("XX-CVG"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.average.25%%").value(930.0))
				.andExpect(MockMvcResultMatchers.jsonPath("$.average.100%%").value(2800));
	}

	@Test
	public void shouldGetNoContentErrorFromGetImpressionCountsOfPercentagesByAdIdFromParameterWhenIdIsNotFound()
			throws Exception {
		this.mockMvc.perform(get("/viewability/impression?id=12313132")).andDo(print())
				.andExpect(status().isNoContent());
	}

	@Test
	public void shouldGetAllImpression() throws Exception {
		this.mockMvc.perform(get("/viewability/impressions")).andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].average.10%%").value(2050.0))
				.andExpect(MockMvcResultMatchers.jsonPath("$.[1].average.100%%").value(2800.0));
	}

	@Test
	public void shouldGetAllImpressionCountOfPercentages() throws Exception {
		this.mockMvc.perform(get("/viewability/impressions/all")).andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.10%%").value(2050.0))
				.andExpect(MockMvcResultMatchers.jsonPath("$.25%%").value(930.0))
				.andExpect(MockMvcResultMatchers.jsonPath("$.100%%").value(2020.0));
	}

	@Test
	public void shouldGetImpressionIdsHavingHigherAverageDurationByViewPercentageAndDuration() throws Exception {
		this.mockMvc.perform(get("/viewability/impressions?view=100&durationHigherThan=800")).andDo(print())
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.containsInAnyOrder("HFC-PKJ", "XX-CVG")));
	}

	@Test
	public void getCorrectRowIds() throws Exception {
		this.mockMvc.perform(get("/viewability/raw/ids")).andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].events", hasSize(2)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value("HFC-PKJ"));
		;
	}
}
