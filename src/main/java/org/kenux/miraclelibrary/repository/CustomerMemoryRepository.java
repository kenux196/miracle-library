package org.kenux.miraclelibrary.repository;

import org.kenux.miraclelibrary.domain.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerMemoryRepository implements CustomerRepository {

    List<Customer> customers = new ArrayList<>();

    @Override
    public void clear() {
        customers.clear();
    }

    @Override
    public Customer save(Customer customer) {
        customer.assignId(getNextId());
        customers.add(customer);
        return customer;
    }

    private Long getNextId() {
        return (long) (customers.size() + 1);
    }

    @Override
    public List<Customer> findAll() {
        return customers;
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customers.stream().filter(customer -> customer.getId().equals(id)).findFirst();
    }

    @Override
    public Optional<Customer> findByName(String name) {
        return customers.stream()
                .filter(customer -> customer.getName().equals(name))
                .findFirst();
    }
}
