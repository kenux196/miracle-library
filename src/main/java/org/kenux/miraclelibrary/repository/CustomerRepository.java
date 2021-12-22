package org.kenux.miraclelibrary.repository;

import org.kenux.miraclelibrary.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByName(String name);

    boolean existsByEmail(String email);
}
