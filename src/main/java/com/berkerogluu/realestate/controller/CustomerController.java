package com.berkerogluu.realestate.controller;

import com.berkerogluu.realestate.model.Customer;
import com.berkerogluu.realestate.service.CustomerService;
import com.berkerogluu.realestate.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "*")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CompanyService companyService; // Required for checkApiKey() method

    // Get all customers - apiKey not required
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    // Get customer by id - apiKey not Required
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Optional<Customer> customer = customerService.getCustomerById(id);
        return customer.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Get customer by email - apiKey not required
    @GetMapping("/email/{email}")
    public ResponseEntity<Customer> getCustomerByEmail(@PathVariable String email) {
        Optional<Customer> customer = customerService.getCustomerByEmail(email);
        return customer.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Get customer by mobile phone number - apiKey not required
    @GetMapping("/mobilePhone/{mobilePhone}")
    public ResponseEntity<Customer> getCustomerByMobilePhone(@PathVariable String mobilePhone) {
        Optional<Customer> customer = customerService.getCustomerByMobilePhone(mobilePhone);
        return customer.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Create a new customer - apiKey is required !
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer, @RequestParam String apiKey) {
        if(!companyService.checkApiKey(apiKey)) {
             return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Check if customer exist by email
        if(customerService.existsByEmail(customer.getEmail())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Customer newCustomer = customerService.createCustomer(customer);
        return ResponseEntity.ok(newCustomer);
    }

}
