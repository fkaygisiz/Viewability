package com.fatih.viewability.dao;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fatih.viewability.dao.DataFileReader.AverageIdImpressionCounter;
import com.fatih.viewability.model.Impression;
import com.fatih.viewability.model.RawEvent;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataFileReaderTests {

	@Autowired
	private DataFileReader dataFileReader;

	@Test
	public void shouldGetReaderWhenFileNameIsGiven() throws IOException {
		BufferedReader reader = dataFileReader.getFileReaderFromTextFile("test_data.txt");
		String readLine = reader.readLine();
		System.out.println(readLine);
		assertTrue(reader != null);
	}

	@Test(expected = Exception.class)
	public void shouldGetExceptionWhenFileDoesNoTExist() throws IOException {
		BufferedReader fileReaderFromTextFile = dataFileReader.getFileReaderFromTextFile("no_test_data.txt");
	}

	@Test
	public void shouldReadAllLinesAndCalculateAveragesFromReader() throws IOException {
		dataFileReader.readAllLinesAndCalculateAverageMaps();

		Map<String, Impression> idAverageImpressionMap = dataFileReader.getIdAverageImpressionMap();
		assertTrue(idAverageImpressionMap.get("XX-CVG").getAverage().get("25%").equals(930.0));

		Map<String, Double> averageDurationOfPercentages = dataFileReader.getAverageDurationOfPercentages();
		assertTrue(Double.valueOf(2020.0).equals(averageDurationOfPercentages.get("100%")));

		List<RawEvent> allEventsById = dataFileReader.getAllEventsById();
		assertTrue(2 == allEventsById.size());
		assertTrue(2 == allEventsById.get(0).getEvents().size() && allEventsById.get(0).getId().equals("HFC-PKJ"));

		List<Impression> idAverageImpressions = dataFileReader.getIdAverageImpressions();

		assertTrue(Double.valueOf(2050.0).equals(idAverageImpressions.get(0).getAverage().get("10%")));

		Map<String, List<AverageIdImpressionCounter>> viewImpressions = dataFileReader.getViewImpressions();
		assertTrue(viewImpressions.get("25%").get(0).getTotalImpressionCount() == 1860);

	}
}
