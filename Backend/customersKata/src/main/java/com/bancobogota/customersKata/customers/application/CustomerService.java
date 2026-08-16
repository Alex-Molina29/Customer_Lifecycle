package com.bancobogota.customersKata.customers.application;

import com.bancobogota.customersKata.customers.application.dto.request.CustomerFilter;
import com.bancobogota.customersKata.customers.application.dto.request.CustomerRequest;

import com.bancobogota.customersKata.customers.domain.Customer;
import com.bancobogota.customersKata.customers.domain.exception.CustomerNotFoundException;

import com.bancobogota.customersKata.customers.infrastructure.CustomerRepository;
import com.bancobogota.customersKata.customers.infrastructure.specification.CustomerSpecification;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer findById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("No se encontró un cliente con el id: " + id));
    }

    public Customer findByDocumentNumber(String documentNumber) {
        Customer customer = customerRepository.findByDocumentNumber(documentNumber);
        if (customer == null) {
            throw new CustomerNotFoundException(
                    "No se encontró un cliente con documento: " + documentNumber);
        }
        return customer;
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Page<Customer> findAllCustomerWithFilters(CustomerFilter filter, Pageable pageable) {
        Specification<Customer> specification = CustomerSpecification.withFilters(
                filter.getDocumentType(),
                filter.getDocumentNumber(),
                filter.getName(),
                filter.getEmail(),
                filter.getPhoneNumber(),
                filter.getSearch()
        );
        return customerRepository.findAll(specification, pageable);
    }

    public List<Customer> createCustomer(List<CustomerRequest> requests) {

        List<Customer> customers = requests.stream()
                .map(request -> Customer.builder()
                    .documentType(request.getDocumentType())
                    .documentNumber(request.getDocumentNumber())
                    .name(request.getName())
                    .email(request.getEmail())
                    .phoneNumber(request.getPhoneNumber())
                    .build())
                .toList();
        return customerRepository.saveAll(customers);
    }

    public Customer updateCustomer(Long id, CustomerRequest request) {
        Customer customer = findById(id);
        customer.setDocumentType(request.getDocumentType());
        customer.setDocumentNumber(request.getDocumentNumber());
        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setPhoneNumber(request.getPhoneNumber());
        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) {
        Customer customer = findById(id);
        customerRepository.delete(customer);
    }
}