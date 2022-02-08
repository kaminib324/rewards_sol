package com.retailer.rewards.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;

@Builder
@Data
public class CustomerRewards implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String name;
	
	@Default
	private Map<String, Long> rewardPoints = new HashMap<>(); 

	private long totalRewardPoints;
}
