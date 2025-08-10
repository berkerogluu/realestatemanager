package com.berkerogluu.realestate.repository;

import com.berkerogluu.realestate.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public Interface CustomerRepository extends JpaRepository <Customer, Long> {

    // Find customer by email
    Optional<Customer> findCustomerById(String email);

    // Find customer by mobile phone
    Optional<Customer> findCustomerByMobilePhone(String mobilePhone);

    // Find customer by home number
    Optional<Customer> findCustomerByHomeNumber(String homeNumber);
}
