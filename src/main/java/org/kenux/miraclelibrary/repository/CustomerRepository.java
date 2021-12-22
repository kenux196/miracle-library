package org.kenux.miraclelibrary.repository;

import org.kenux.miraclelibrary.domain.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository {

    void clear();

    Customer save(Customer customer);

    List<Customer> findAll();

    Optional<Customer> findById(Long id);

    Optional<Customer> findByName(String customer1);
}
