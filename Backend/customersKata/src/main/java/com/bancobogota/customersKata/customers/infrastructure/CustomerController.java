package com.bancobogota.customersKata.customers.infrastructure;

import com.bancobogota.customersKata.customers.application.CustomerService;
import com.bancobogota.customersKata.customers.application.dto.request.CustomerFilter;
import com.bancobogota.customersKata.customers.application.dto.request.CustomerRequest;
import com.bancobogota.customersKata.customers.application.dto.response.CustomerEntryList;
import com.bancobogota.customersKata.customers.application.dto.response.CustomerList;
import com.bancobogota.customersKata.customers.application.dto.response.CustomerResponse;
import com.bancobogota.customersKata.customers.domain.Customer;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/")
    public ResponseEntity<List<CustomerResponse>> save(
            @Valid @RequestBody List<CustomerRequest> requests,
            UriComponentsBuilder uriBuilder
    ) {
        List<Customer> savedCustomers = customerService.createCustomer(requests);
        List<CustomerResponse> response = savedCustomers.stream().map(CustomerResponse::of).toList();

        return ResponseEntity.status(org.springframework.http.HttpStatus.CREATED).body(response);
    }

    @GetMapping("/")
    public ResponseEntity<CustomerList> getCustomers(
            @ModelAttribute CustomerFilter filter,
            Pageable pageable
    ) {
        Page<Customer> page = customerService.findAllCustomerWithFilters(filter, pageable);

        CustomerList response = new CustomerList(
                page.getTotalPages(),
                page.getTotalElements(),
                page.stream().map(CustomerEntryList::of).toList()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Customer>> findAll(Pageable pageable) {
        try {
            return ResponseEntity.ok(customerService.findAll());

        } catch (RuntimeException e) {
            throw new RuntimeException("Error al obtener los clientes: " + e.getMessage(), e);
        }
    }

    @GetMapping("/document/{documentNumber}")
    public ResponseEntity<Customer> getCustomerByDocumentNumber(
            @PathVariable String documentNumber
    ) {
        return ResponseEntity.ok(customerService.findByDocumentNumber(documentNumber));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(customerService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody CustomerRequest request
    ) {
        return ResponseEntity.ok(CustomerResponse.of(customerService.updateCustomer(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
