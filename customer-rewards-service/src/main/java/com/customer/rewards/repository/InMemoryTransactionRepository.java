package com.customer.rewards.repository;

import com.customer.rewards.model.Transaction;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class InMemoryTransactionRepository {

    private final Map<Long, Transaction> store = new HashMap<>();
    private final AtomicLong idGen = new AtomicLong(1);

    public Transaction save(Transaction t) {
        if (t.getId() == null) {
            t.setId(idGen.getAndIncrement());
        }
        store.put(t.getId(), t);
        return t;
    }

    public List<Transaction> findByCustomerId(Integer customerId) {
        return store.values().stream()
                .filter(tx -> tx.getCustomerId().equals(customerId))
                .collect(Collectors.toList());
    }

    public List<Transaction> findByCustomerIdAndDateBetween(Integer customerId, LocalDate start, LocalDate end) {
        return store.values().stream()
                .filter(tx -> tx.getCustomerId().equals(customerId))
                .filter(tx -> !tx.getDate().isBefore(start) && !tx.getDate().isAfter(end))
                .collect(Collectors.toList());
    }

    public List<Transaction> findAll() {
        return new ArrayList<>(store.values());
    }
}
