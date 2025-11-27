package com.customer.rewards.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.customer.rewards.model.Customer;

@Repository
public class InMemoryCustomerRepository {

    private final Map<Integer, Customer> store = new HashMap<>();

    public Optional<Customer> findById(Integer id) {
        return Optional.ofNullable(store.get(id));
    }

    public void save(Customer c) {
        store.put(c.getId(), c);
    }

    public List<Customer> findAll() {
        return new ArrayList<>(store.values());
    }
}
