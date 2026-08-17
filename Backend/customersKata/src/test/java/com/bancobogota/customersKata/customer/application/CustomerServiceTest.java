package com.bancobogota.customersKata.customer.application;

import com.bancobogota.customersKata.customers.application.CustomerService;
import com.bancobogota.customersKata.customers.application.dto.request.CustomerRequest;
import com.bancobogota.customersKata.customers.domain.Customer;
import com.bancobogota.customersKata.customers.domain.exception.CustomerNotFoundException;
import com.bancobogota.customersKata.customers.infrastructure.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void deberiaEncontrarClientePorId() {
        Customer customer = Customer.builder().id(1L).name("Sujeto Pruebas").email("sj@correo.com").build();
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        Customer customer1 = customerService.findById(1L);

        assertThat(customer1.getName()).isEqualTo("Sujeto Pruebas");
    }

    @Test
    void deberiaLanzarExcepcionSiElClienteNoExiste() {
        when(customerRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> customerService.findById(99L)).isInstanceOf(CustomerNotFoundException.class);
    }

    @Test
    void deberiaCrearClientesCorrectamente() {
        // Arrange
        CustomerRequest request = new CustomerRequest();
        request.setDocumentType("CC");
        request.setDocumentNumber("123456");
        request.setName("Sujeto Pruebas");
        request.setEmail("sj@correo.com");
        request.setPhoneNumber("3001234567");

        Customer guardado = Customer.builder()
                .id(1L)
                .name("Sujeto Pruebas")
                .email("sj@correo.com")
                .build();

        when(customerRepository.saveAll(anyList())).thenReturn(List.of(guardado));

        // Act
        List<Customer> result = customerService.createCustomer(List.of(request));

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Sujeto Pruebas");
    }
}
