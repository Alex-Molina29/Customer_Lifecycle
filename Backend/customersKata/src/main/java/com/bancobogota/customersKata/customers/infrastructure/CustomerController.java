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
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponse> save(
            @Valid @RequestBody CustomerRequest request,
            UriComponentsBuilder uriBuilder
    ) {
        Customer saved = customerService.createCustomer(request);
        URI location = uriBuilder.path("/api/customers/{documentNumber}")
                .buildAndExpand(saved.getDocumentNumber())
                .toUri();
        return ResponseEntity.created(location).body(CustomerResponse.of(saved));
    }

    @GetMapping
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
}
