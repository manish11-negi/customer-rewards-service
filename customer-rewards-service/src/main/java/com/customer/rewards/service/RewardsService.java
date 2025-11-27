package com.customer.rewards.service;

import com.customer.rewards.dto.RewardsResponseDto;
import com.customer.rewards.dto.TransactionDto;
import com.customer.rewards.exception.ResourceNotFoundException;
import com.customer.rewards.model.Customer;
import com.customer.rewards.model.Transaction;
import com.customer.rewards.repository.InMemoryCustomerRepository;
import com.customer.rewards.repository.InMemoryTransactionRepository;
import com.customer.rewards.util.RewardsCalculator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class RewardsService {
	
    InMemoryCustomerRepository customerRepo;
    InMemoryTransactionRepository transactionRepo;
	
	 public RewardsService(InMemoryCustomerRepository customerRepository, InMemoryTransactionRepository transactionRepository) {
	        this.customerRepo = customerRepository;
	        this.transactionRepo = transactionRepository;
	    }

	 public RewardsResponseDto getRewardsForCustomer(Integer customerId, LocalDate start, LocalDate end) {

		 log.info("Calculate rewards for customer {} from {} to {}", customerId, start, end);
		 
		 Customer customer = customerRepo.findById(customerId)
				 .orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + customerId));
		 
		 List<Transaction> txs = transactionRepo.findByCustomerIdAndDateBetween(customerId, start, end);
		 
		 List<TransactionDto> txDtos = convertToTransactionDtos(txs);
		 
		 int totalPoints = txDtos.stream().mapToInt(TransactionDto::getPoints).sum();

		 return RewardsResponseDto.builder()
				 .customerId(customer.getId())
				 .customerName(customer.getName())
				 .totalPoints(totalPoints)
				 .transactions(txDtos)
				 .build();
	 }
    
	 private List<TransactionDto> convertToTransactionDtos(List<Transaction> transactions) {
		 List<TransactionDto> dtos = new ArrayList<>();

		 for (Transaction tx : transactions) {
			 int points = RewardsCalculator.calculatePoints(tx.getAmount());

			 dtos.add(TransactionDto.builder()
					 .id(tx.getId())
					 .date(tx.getDate())
					 .amount(tx.getAmount())
					 .points(points)
					 .build());
		 }

		 dtos.sort((a, b) -> b.getDate().compareTo(a.getDate()));
		 return dtos;
	 }


    public RewardsResponseDto getRewardsForCustomerWithMonths(Integer customerId, Integer months) {
        if (months == null) {
        	months = 3;
        }
        LocalDate end = LocalDate.now();
        LocalDate start = end.minusMonths(months);
        return getRewardsForCustomer(customerId, start, end);
    }

    @Async("taskExecutor")
    public CompletableFuture<RewardsResponseDto> getRewardsForCustomerAsync(Integer customerId, Integer months) {
        RewardsResponseDto res = getRewardsForCustomerWithMonths(customerId, months);
        return CompletableFuture.completedFuture(res);
    }
}
