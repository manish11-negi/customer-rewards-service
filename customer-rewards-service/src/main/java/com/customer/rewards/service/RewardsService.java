package com.customer.rewards.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.customer.rewards.dto.RewardsResponseDto;
import com.customer.rewards.dto.TransactionDto;
import com.customer.rewards.exception.ResourceNotFoundException;
import com.customer.rewards.model.Customer;
import com.customer.rewards.model.Transaction;
import com.customer.rewards.repository.InMemoryCustomerRepository;
import com.customer.rewards.repository.InMemoryTransactionRepository;
import com.customer.rewards.util.RewardsCalculator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RewardsService {
	
    private final InMemoryCustomerRepository customerRepository;
    private final InMemoryTransactionRepository transactionRepository;
	
	 public RewardsService(InMemoryCustomerRepository customerRepository, InMemoryTransactionRepository transactionRepository) {
	        this.customerRepository = customerRepository;
	        this.transactionRepository = transactionRepository;
	    }

	 public RewardsResponseDto getRewardsForCustomer(Integer customerId, LocalDate start, LocalDate end) {

		 log.info("Calculate rewards for customer {} from {} to {}", customerId, start, end);
		 
		 Customer customer = customerRepository.findById(customerId)
				 .orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + customerId));
		 
		 List<Transaction> transactions = transactionRepository.findByCustomerIdAndDateBetween(customerId, start, end);
		 
		 List<TransactionDto> transactionDtos = convertToTransactionDtos(transactions);
		 
		 int totalRewardPoints = transactionDtos.stream().mapToInt(TransactionDto::getRewardPoints).sum();

		 return RewardsResponseDto.builder()
				 .customerId(customer.getId())
				 .customerName(customer.getName())
				 .totalRewardPoints(totalRewardPoints)
				 .totalTransactions(transactionDtos.size())
				 .monthlyRewardTransactions(transactionDtos)
				 .build();
	 }
    
	 private List<TransactionDto> convertToTransactionDtos(List<Transaction> transactions) {
		 return transactions.stream()
				 .map(transaction -> TransactionDto.builder()
						 .id(transaction.getId())
						 .year(transaction.getDate().getYear())
						 .monthName(transaction.getDate().format(DateTimeFormatter.ofPattern("MMMM")))
						 .month(transaction.getDate().getMonthValue())
						 .amount(transaction.getAmount())
						 .rewardPoints(RewardsCalculator.calculatePoints(transaction.getAmount()))
						 .build())
				 .sorted(
						 Comparator.comparing(TransactionDto::getYear).reversed()
						 .thenComparing(Comparator.comparing(TransactionDto::getMonth).reversed())
						 )

				 .toList(); 
	 }


    public RewardsResponseDto getRewardsForCustomerWithMonths(Integer customerId, Integer months) {
        if (months == null) {
        	months = 3;
        }
        LocalDate end = LocalDate.now(ZoneId.systemDefault());
        LocalDate start = end.minusMonths(months);
        return getRewardsForCustomer(customerId, start, end);
    }
}
