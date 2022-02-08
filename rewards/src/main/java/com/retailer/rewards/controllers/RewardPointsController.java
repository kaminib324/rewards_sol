package com.retailer.rewards.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retailer.rewards.models.CustomerRewards;
import com.retailer.rewards.services.RewardsService;

@RestController
@RequestMapping("/rewards")
public class RewardPointsController {

	@Autowired
	private RewardsService rewardsService;

	@GetMapping
	public ResponseEntity<List<CustomerRewards>> getRewards() {
		return new ResponseEntity<List<CustomerRewards>>(rewardsService.getRewards(), HttpStatus.OK);
	}
}
