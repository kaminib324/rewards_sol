package com.retailer.rewards.services;

import static com.retailer.rewards.constants.BeanName.TRANSACTIONS_BEAN;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.retailer.rewards.models.Customer;
import com.retailer.rewards.models.CustomerRewards;
import com.retailer.rewards.models.Transaction;

import lombok.val;

@Service
public class RewardsServiceImpl implements RewardsService {

	@Autowired
	@Qualifier(TRANSACTIONS_BEAN)
	private List<Transaction> transactions;

	@Override
	public List<CustomerRewards> getRewards() {
		val customerWiseTransactions = getCustomerWiseTransactions();
		List<CustomerRewards> customerRewards = new ArrayList<>();
		customerWiseTransactions.entrySet().forEach(entry -> {
			val transactions = entry.getValue();
			val customer = getCustomer(transactions);
			customerRewards.add(getCustomerRewards(transactions, customer));
		});
		return customerRewards;
	}

	private CustomerRewards getCustomerRewards(List<Transaction> transactions, Customer customer) {
		return CustomerRewards.builder().id(customer.getId()).name(customer.getName())
				.rewardPoints(getDateWiseRewards(transactions)).totalRewardPoints(getConsolidatedRewards(transactions))
				.build();
	}

	private long getConsolidatedRewards(List<Transaction> transactions) {
		return transactions.stream().map(transaction -> calculateRewards(transaction.getAmount()))
				.collect(Collectors.summingLong(Long::longValue));
	}

	private Map<String, Long> getDateWiseRewards(List<Transaction> transactions) {
		Map<String, List<Transaction>> groupTransactionsByMonthAndYear = groupTransactionsByMonthAndYear(transactions);
		Map<String, Long> monthlyRewards = new HashMap<>();
		groupTransactionsByMonthAndYear.entrySet().forEach(entry -> {
			monthlyRewards.put(entry.getKey(), getConsolidatedRewards(entry.getValue()));
		});
		return monthlyRewards;
	}

	private Map<String, List<Transaction>> groupTransactionsByMonthAndYear(List<Transaction> transactions) {
		return transactions.stream()
				.collect(Collectors.groupingBy(
						transaction -> transaction.getDate().getMonth().name() + "-" + transaction.getDate().getYear(),
						Collectors.mapping(transaction -> transaction, Collectors.toList())));
	}

	private Customer getCustomer(List<Transaction> transactions) {
		return transactions.get(0).getCustomer();
	}

	private Map<String, List<Transaction>> getCustomerWiseTransactions() {
		return transactions.stream().collect(Collectors.groupingBy(transaction -> transaction.getCustomer().getId(),
				Collectors.mapping(transaction -> transaction, Collectors.toList())));
	}

	private Long calculateRewards(Long amount) {
		Long totalRewards = 0L;

		Long amountGreaterThanHunder = amount - 100;
		if (amountGreaterThanHunder > 0) {
			totalRewards = amountGreaterThanHunder * 2;
			Long amountGreaterThanFifty = (amount - amountGreaterThanHunder) - 50;
			if (amountGreaterThanFifty > 0) {
				totalRewards += amountGreaterThanFifty;
			}
		} else {
			Long amountGreaterThanFifty = amount - 50;
			if (amountGreaterThanFifty > 0) {
				totalRewards += amountGreaterThanFifty;
			}
		}

		return totalRewards;
	}

}
