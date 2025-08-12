package com.berkerogluu.realestate.repository;

import com.berkerogluu.realestate.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // https://www.baeldung.com/spring-data-derived-queries

    // Fİnd customer using email
    Optional<Customer> findByEmail(String email);

    // Find customer by mobile phone number
    Optional<Customer> findByMobilePhone(String mobilePhone);

    // Check if customer exist
    boolean existsByEmail(String email);
}
