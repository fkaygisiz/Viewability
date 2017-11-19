package com.fatih.viewability.dao;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.fatih.viewability.model.Impression;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ViewabilityDAOTests {

	@Autowired
	private ViewabilityDAO viewabilityDAO;

	@MockBean
	private DataFileReader dataFileReader;

	@Test
	public void shouldGetImpressionWithCorrectPercentageImpressionCountsWhenIdIsGiven() {
		Map<String, Impression> value = new HashMap<>();
		value.put("1", new Impression("1", new HashMap<String, Double>() {
			{
				put("10%", 123.0);
				put("25%", 856.0);
				put("50%", 325.0);
				put("100%", 98.0);
			}
		}));
		value.put("2", new Impression("2", new HashMap<String, Double>() {
			{
				put("10%", 20.0);
				put("25%", 86.0);
			}
		}));
		when(dataFileReader.getIdAverageImpressionMap()).thenReturn(value);
		Impression actual = viewabilityDAO.getImpressionById("2");
		assertEquals("2", actual.getId());
		assertEquals(Double.valueOf(20.0), actual.getAverage().get("10%"));
		assertEquals(Double.valueOf(86), actual.getAverage().get("25%"));
	}

}
