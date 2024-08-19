package com.malgo.malgo.service;

import com.malgo.malgo.model.Customer;
import com.malgo.malgo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public Optional<Customer> findByUsername(String username) {
        Optional<Customer> customer = customerRepository.findByUsername(username);
        return customer;
    }

    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public Iterable<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }
}