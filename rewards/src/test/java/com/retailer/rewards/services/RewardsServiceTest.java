package com.retailer.rewards.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.retailer.rewards.models.CustomerRewards;

@SpringBootTest
public class RewardsServiceTest {

	private static final String JOHN_ID = "b3bb57fd-c609-4fcc-b21a-f43e35355235";
	private static final String MIKE_ID = "148f8210-e24e-4955-a5c0-95c5c25b3f65";
	
	@Autowired
	private RewardsService rewardsService;

	@Test
	public void testRewards() {
		List<CustomerRewards> customerRewards = rewardsService.getRewards();
		assertFalse(customerRewards.isEmpty());
		assertEquals(2, customerRewards.size());
		
		CustomerRewards customerJohn = customerRewards.stream().filter(customer -> customer.getId().equals(JOHN_ID)).findFirst().orElse(null);
		assertFalse(null == customerJohn);
		assertEquals(140, customerJohn.getTotalRewardPoints());
		assertTrue(customerJohn.getRewardPoints().containsKey("FEBRUARY-2022"));
		assertEquals(140, customerJohn.getRewardPoints().get("FEBRUARY-2022"));
		
		CustomerRewards customerMike = customerRewards.stream().filter(customer -> customer.getId().equals(MIKE_ID)).findFirst().orElse(null);
		assertFalse(null == customerMike);
		assertEquals(335, customerMike.getTotalRewardPoints());
		assertTrue(customerMike.getRewardPoints().containsKey("FEBRUARY-2022"));
		assertEquals(45, customerMike.getRewardPoints().get("FEBRUARY-2022"));
		assertTrue(customerMike.getRewardPoints().containsKey("JANUARY-2022"));
		assertEquals(230, customerMike.getRewardPoints().get("JANUARY-2022"));
		assertTrue(customerMike.getRewardPoints().containsKey("JUNE-2021"));
		assertEquals(60, customerMike.getRewardPoints().get("JUNE-2021"));
	}

}
