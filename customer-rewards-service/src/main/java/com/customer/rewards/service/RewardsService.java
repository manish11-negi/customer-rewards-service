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
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class RewardsService {
	
	@Autowired
    InMemoryCustomerRepository customerRepo;
	@Autowired
    InMemoryTransactionRepository transactionRepo;

    public RewardsResponseDto getRewardsForCustomer(Integer customerId, LocalDate start, LocalDate end) {
    	int total = 0;
        log.info("Calculate rewards for customer {} from {} to {}", customerId, start, end);
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + customerId));
        List<Transaction> txs = transactionRepo.findByCustomerIdAndDateBetween(customerId, start, end);

        Map<String, Integer> monthlyPoints = new TreeMap<>();
      
        List<TransactionDto> txDtos = new ArrayList<>();

        for (Transaction tx : txs) {
            int points = RewardsCalculator.calculatePoints(tx.getAmount());
            total += points;
            String month = tx.getDate().getMonth().toString();
            monthlyPoints.put(month, monthlyPoints.getOrDefault(month, 0) + points);
            txDtos.add(TransactionDto.builder()
                    .id(tx.getId())
                    .date(tx.getDate())
                    .amount(tx.getAmount())
                    .points(points)
                    .build());
        }

        txDtos.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));

        RewardsResponseDto resp = RewardsResponseDto.builder()
                .customerId(customer.getId())
                .customerName(customer.getName())
                .monthlyPoints(monthlyPoints)
                .totalPoints(total)
                .transactions(txDtos)
                .build();

        log.debug("Total points {} for customer {}", total, customerId);
        return resp;
    }

    public RewardsResponseDto getRewardsForCustomerWithMonths(Integer customerId, Integer months) {
        if (months == null)
        months = 3;
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
