package com.fatih.viewability;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fatih.viewability.controller.ViewabilityController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ViewabilityApplicationTests {

	@Autowired
	private ViewabilityController viewabilityController;

	@Test
	public void contextLoads() {
		assertNotNull(viewabilityController);
	}

}
