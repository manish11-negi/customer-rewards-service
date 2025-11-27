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

    InMemoryCustomerRepository customerRepo;
    InMemoryTransactionRepository transactionRepo;
	
	 public DataInitializer(InMemoryCustomerRepository customerRepository, InMemoryTransactionRepository transactionRepository) {
	        this.customerRepo = customerRepository;
	        this.transactionRepo = transactionRepository;
	    }

    @Override
    public void run(String... args) {
        customerRepo.save(new Customer(1, "John Doe"));
        customerRepo.save(new Customer(2, "Alice Smith"));
        customerRepo.save(new Customer(3, "Bob Johnson"));

        transactionRepo.save(new Transaction(null, 1, 120.0, LocalDate.of(2025,11,15)));
        transactionRepo.save(new Transaction(null, 1, 75.0, LocalDate.of(2025,11,28)));
        transactionRepo.save(new Transaction(null, 1, 200.0, LocalDate.of(2025,12,18)));
        transactionRepo.save(new Transaction(null, 2, 95.0, LocalDate.of(2026,1,10)));
        transactionRepo.save(new Transaction(null, 2, 130.0, LocalDate.of(2026,12,5)));
        transactionRepo.save(new Transaction(null, 2, 95.0, LocalDate.of(2025,11,10)));
        transactionRepo.save(new Transaction(null, 2, 130.0, LocalDate.of(2025,10,5)));
        transactionRepo.save(new Transaction(null, 3, 80.0, LocalDate.of(2025,10,01)));
        transactionRepo.save(new Transaction(null, 3, 120.0, LocalDate.of(2025,11,21)));
    }
}
