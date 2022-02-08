package com.retailer.rewards.config;

import static com.retailer.rewards.constants.BeanName.TRANSACTIONS_BEAN;

import java.io.File;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.retailer.rewards.models.Transaction;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class LoadData {

	@Bean(TRANSACTIONS_BEAN)
	public List<Transaction> loadTransactions() {
		try {
			File file = ResourceUtils.getFile("classpath:data.json");
			List<Transaction> transactions = new ObjectMapper().findAndRegisterModules()
					.readValue(file, new TypeReference<List<Transaction>>() {
			});
			return transactions;
		} catch (Exception e) {
			log.error("Could not load data ", e);
		}
		return List.of();
	}
}
