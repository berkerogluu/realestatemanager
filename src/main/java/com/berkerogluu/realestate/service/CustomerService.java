package com.berkerogluu.realestate.service;

import com.berkerogluu.realestate.model.Customer;
import com.berkerogluu.realestate.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    // Get all customers
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // Get customer by id
    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    // Get customer by email
    public Optional<Customer> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    // Get customer by mobile phone number
    public Optional<Customer> getCustomerByMobilePhone(String mobilePhone) {
        return customerRepository.findByMobilePhone(mobilePhone);
    }

    // Check if customer exists by email
    public boolean existsByEmail(String email) {
        return customerRepository.existsByEmail(email);
    }

    // Create customer
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    // Delete customer by id
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
