package com.meetrics.viewability.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.meetrics.viewability.model.Impression;

@Service
public class VieawibilityServiceImpl implements VieawibilityService {

	@Override
	public Impression getImpressionById(Long id) {
		return new Impression(1L, new HashMap<String, Integer>() {
			{
				put("10%", 123);
				put("25%", 856);
				put("50%", 325);
				put("100%", 98);
			}
		});
	}

	@Override
	public List<Impression> getAllImpressions() {
		return new ArrayList<Impression>() {
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
	}

}
