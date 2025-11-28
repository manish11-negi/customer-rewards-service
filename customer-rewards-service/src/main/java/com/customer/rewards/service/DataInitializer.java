package com.customer.rewards.service;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.customer.rewards.model.Customer;
import com.customer.rewards.model.Transaction;
import com.customer.rewards.repository.InMemoryCustomerRepository;
import com.customer.rewards.repository.InMemoryTransactionRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final InMemoryCustomerRepository customerRepository;
    private final InMemoryTransactionRepository transactionRepository;
	
	 public DataInitializer(InMemoryCustomerRepository customerRepository, InMemoryTransactionRepository transactionRepository) {
	        this.customerRepository = customerRepository;
	        this.transactionRepository = transactionRepository;
	    }

    @Override
    public void run(String... args) {
    	customerRepository.save(new Customer(1, "John Doe"));
    	customerRepository.save(new Customer(2, "Alice Smith"));
    	customerRepository.save(new Customer(3, "Bob Johnson"));

    	transactionRepository.save(new Transaction(null, 1, 120.0, LocalDate.of(2025,11,15)));
    	transactionRepository.save(new Transaction(null, 1, 75.0, LocalDate.of(2025,11,28)));
    	transactionRepository.save(new Transaction(null, 1, 200.0, LocalDate.of(2025,12,18)));
    	transactionRepository.save(new Transaction(null, 2, 95.0, LocalDate.of(2026,1,10)));
    	transactionRepository.save(new Transaction(null, 2, 130.0, LocalDate.of(2026,12,5)));
    	transactionRepository.save(new Transaction(null, 2, 95.0, LocalDate.of(2025,11,10)));
    	transactionRepository.save(new Transaction(null, 2, 130.0, LocalDate.of(2025,10,5)));
    	transactionRepository.save(new Transaction(null, 3, 80.0, LocalDate.of(2025,10,01)));
    	transactionRepository.save(new Transaction(null, 3, 120.0, LocalDate.of(2025,11,21)));
    }
}
