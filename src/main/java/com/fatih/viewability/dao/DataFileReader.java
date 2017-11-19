package com.fatih.viewability.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fatih.viewability.model.Event;
import com.fatih.viewability.model.Impression;
import com.fatih.viewability.model.RawEvent;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

@Component
public class DataFileReader {

	@Value("${data.file.name}")
	private String fileName;

	private Map<String, Impression> idAverageImpressionMap;
	private Map<String, Double> averageDurationOfPercentages;
	private List<RawEvent> allEventsById;
	private List<Impression> idAverageImpressions;
	private Map<String, List<AverageIdImpressionCounter>> viewImpressions;

	public BufferedReader getFileReaderFromTextFile(String fileName) {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(fileName);
		return new BufferedReader(new InputStreamReader(is));
	}

	@PostConstruct
	public void readAllLinesAndCalculateAverageMaps() throws IOException {
		BufferedReader fileReaderFromTextFile = this.getFileReaderFromTextFile(fileName);
		Map<String, RawEvent> allEventsByIdMap = new HashMap<>();
		Table<String, String, AverageIdImpressionCounter> idPercentageTable = HashBasedTable.create();
		String line = fileReaderFromTextFile.readLine();// To skip the first line
		line = fileReaderFromTextFile.readLine();
		while (!StringUtils.isEmpty(line)) {
			String[] split = line.split("\t");
			AverageIdImpressionCounter averageIdImpressionCounter2 = idPercentageTable.get(split[0], split[1] + "%");
			if (averageIdImpressionCounter2 == null) {
				idPercentageTable.put(split[0], split[1] + "%",
						new AverageIdImpressionCounter(1, split[0], Integer.valueOf(split[2])));

			} else {
				averageIdImpressionCounter2.add(Integer.valueOf(split[2]));
			}
			RawEvent rawEvent = allEventsByIdMap.get(split[0]);
			if (rawEvent == null) {
				rawEvent = new RawEvent(split[0]);
				allEventsByIdMap.put(split[0], rawEvent);
			}
			rawEvent.getEvents().add(new Event(Integer.valueOf(split[1]), Integer.valueOf(split[2])));

			line = fileReaderFromTextFile.readLine();
		}

		idAverageImpressionMap = idPercentageTable.rowMap().entrySet().stream()
				.collect(Collectors.toMap(f -> f.getKey(), f -> new Impression(f.getKey(),
						convertAverageIdImpressionCounterToPercentageAverageMap(f.getValue()))));

		idAverageImpressions = new ArrayList<>(idAverageImpressionMap.values());
		Collections.sort(idAverageImpressions);

		Map<String, Map<String, AverageIdImpressionCounter>> percentageImpressionMap = idPercentageTable.columnMap();

		viewImpressions = percentageImpressionMap.entrySet().stream().collect(
				Collectors.toMap(e -> e.getKey(), e -> e.getValue().values().stream().collect(Collectors.toList())));

		averageDurationOfPercentages = percentageImpressionMap.entrySet().stream()
				.collect(Collectors.toMap(e -> e.getKey(), e -> findAverageOfPercentage(e.getValue())));

		allEventsById = new ArrayList<>(allEventsByIdMap.values());
		Collections.sort(allEventsById);

		fileReaderFromTextFile.close();

	}

	private Double findAverageOfPercentage(Map<String, AverageIdImpressionCounter> idViewsPerPercentage) {
		Integer totalImpressionCount = idViewsPerPercentage.values().stream().map(e -> e.getTotalImpressionCount())
				.reduce(0, (x, y) -> x + y);
		Integer totalEventCount = idViewsPerPercentage.values().stream().map(e -> e.getTotalEventCount()).reduce(0,
				(x, y) -> x + y);
		return Double.valueOf(totalImpressionCount) / totalEventCount;
	}

	private Map<String, Double> convertAverageIdImpressionCounterToPercentageAverageMap(
			Map<String, AverageIdImpressionCounter> row) {
		return row.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().getAverage()));
	}

	public class AverageIdImpressionCounter {
		private int totalEventCount;
		private int totalImpressionCount;
		private String id;

		public AverageIdImpressionCounter(int totalEventCount, String id, int totalImpressionCount) {
			this.totalEventCount = totalEventCount;
			this.totalImpressionCount = totalImpressionCount;
			this.id = id;
		}

		public int getTotalEventCount() {
			return totalEventCount;
		}

		public int getTotalImpressionCount() {
			return totalImpressionCount;
		}

		public void add(Integer impressionCount) {
			this.totalEventCount++;
			this.totalImpressionCount += impressionCount;

		}

		public Double getAverage() {
			return Double.valueOf(totalImpressionCount) / totalEventCount;
		}

		public String getId() {
			return id;
		}

	}

	public Map<String, Impression> getIdAverageImpressionMap() {
		return idAverageImpressionMap;
	}

	public Map<String, Double> getAverageDurationOfPercentages() {
		return averageDurationOfPercentages;
	}

	public List<RawEvent> getAllEventsById() {
		return allEventsById;
	}

	public List<Impression> getIdAverageImpressions() {
		return idAverageImpressions;
	}

	public Map<String, List<AverageIdImpressionCounter>> getViewImpressions() {
		return viewImpressions;
	}

}
