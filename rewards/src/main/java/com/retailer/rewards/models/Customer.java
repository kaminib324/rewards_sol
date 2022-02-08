package com.retailer.rewards.models;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Data
public class Customer {

	private String id;
	
	private String name;
	
}
