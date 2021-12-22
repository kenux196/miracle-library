package org.kenux.miraclelibrary.repository;

import org.kenux.miraclelibrary.domain.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerMemoryRepository {

    List<Customer> customers = new ArrayList<>();

    public void clear() {
        customers.clear();
    }

    public Customer save(Customer customer) {
        customer.assignId(getNextId());
        customers.add(customer);
        return customer;
    }

    private Long getNextId() {
        return (long) (customers.size() + 1);
    }

    public List<Customer> findAll() {
        return customers;
    }

    public Optional<Customer> findById(Long id) {
        return customers.stream().filter(customer -> customer.getId().equals(id)).findFirst();
    }

    public Optional<Customer> findByName(String name) {
        return customers.stream()
                .filter(customer -> customer.getName().equals(name))
                .findFirst();
    }

    public boolean existsByEmail(String email) {
        return customers.stream()
                .anyMatch(customer -> customer.getEmail().equals(email));
    }
}
