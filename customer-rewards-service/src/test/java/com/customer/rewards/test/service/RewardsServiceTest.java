package com.customer.rewards.test.service;

import com.customer.rewards.dto.RewardsResponseDto;
import com.customer.rewards.exception.ResourceNotFoundException;
import com.customer.rewards.model.Customer;
import com.customer.rewards.model.Transaction;
import com.customer.rewards.repository.InMemoryCustomerRepository;
import com.customer.rewards.repository.InMemoryTransactionRepository;
import com.customer.rewards.service.RewardsService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RewardsServiceTest {

	@InjectMocks
    private RewardsService rewardsService;
    @Mock
    private InMemoryCustomerRepository customerRepo;
    @Mock
    private InMemoryTransactionRepository transactionRepo;

    @Test
    void testGetRewardsForCustomer_success() {
        Integer customerId = 1;
        LocalDate start = LocalDate.of(2024, 1, 1);
        LocalDate end = LocalDate.of(2024, 1, 31);

        Customer customer = new Customer(1, "John Doe");

        Transaction t1 = new Transaction(null, 1, 120.0, LocalDate.of(2024, 1, 5));
        Transaction t2 = new Transaction(null, 2, 80.0, LocalDate.of(2024, 1, 10));

        when(customerRepo.findById(eq(customerId))).thenReturn(Optional.of(customer));
        when(transactionRepo.findByCustomerIdAndDateBetween(eq(customerId), eq(start), eq(end)))
                .thenReturn(List.of(t1, t2));

        RewardsResponseDto response = rewardsService.getRewardsForCustomer(customerId, start, end);

        assertNotNull(response);
        assertEquals(1, response.getCustomerId());
        assertEquals("John Doe", response.getCustomerName());
        assertEquals(2, response.getTransactions().size());
        assertEquals(120, response.getTotalPoints());
    }

    @Test
    void testGetRewardsForCustomer_customerNotFound() {
        when(customerRepo.findById(5)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                rewardsService.getRewardsForCustomer(5, LocalDate.now(), LocalDate.now()));
    }

    @Test
    void testGetRewardsForCustomerWithMonths_defaultMonths() {
        Integer customerId = 1;

        when(customerRepo.findById(customerId))
                .thenReturn(Optional.of(new Customer(1, "John")));

        when(transactionRepo.findByCustomerIdAndDateBetween(Mockito.eq(customerId), Mockito.any(), Mockito.any()))
                .thenReturn(List.of());

        RewardsResponseDto response = rewardsService.getRewardsForCustomerWithMonths(customerId, null);

        assertNotNull(response);
        assertEquals(1, response.getCustomerId());
    }

    @Test
    void testGetRewardsForCustomerAsync() throws Exception {
        Integer customerId = 1;

        when(customerRepo.findById(customerId))
                .thenReturn(Optional.of(new Customer(1, "John")));

        when(transactionRepo.findByCustomerIdAndDateBetween(Mockito.eq(customerId), Mockito.any(), Mockito.any()))
                .thenReturn(List.of());

        CompletableFuture<RewardsResponseDto> future =
                rewardsService.getRewardsForCustomerAsync(customerId, 2);

        RewardsResponseDto resp = future.get();

        assertNotNull(resp);
        assertEquals(1, resp.getCustomerId());
    }
}
