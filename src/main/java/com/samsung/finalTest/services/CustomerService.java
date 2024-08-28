package com.samsung.finalTest.services;

import com.samsung.finalTest.repositories.Customer;
import com.samsung.finalTest.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;


    public Customer addCustomer(Customer customer) {
        if (customer.getCustomerNumber() == null || customer.getCustomerNumber().isEmpty()) {
            throw new IllegalArgumentException("Customer number is required");
        }

        if (customerRepository.existsByCustomerNumber(customer.getCustomerNumber())) {
            throw new IllegalArgumentException("Duplicate customer number");
        }
        if (customerRepository.existsByEmail(customer.getEmail())) {
            throw new IllegalArgumentException("Duplicate email");
        }
        customer.setCreatedAt(LocalDateTime.now());
        return customerRepository.save(customer);
    }
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}
