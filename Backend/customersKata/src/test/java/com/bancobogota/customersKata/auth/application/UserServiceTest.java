package com.bancobogota.customersKata.auth.application;

import com.bancobogota.customersKata.auth.application.dto.request.CreateUserRequest;
import com.bancobogota.customersKata.auth.application.dto.response.CreateUserResponse;
import com.bancobogota.customersKata.auth.domain.User;
import com.bancobogota.customersKata.auth.domain.exception.UsernameAlreadyExistsException;
import com.bancobogota.customersKata.auth.infrastructure.UserRespository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRespository userRespository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void deberiaCrearUsuarioCuandoNoExiste() throws UsernameAlreadyExistsException {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("prueba");
        request.setPassword("password");
        request.setRol("USER");

        when(userRespository.findByUsername("prueba")).thenReturn(Optional.empty());
        when(passwordEncoder.encode(request.getPassword())).thenReturn("hashSimulado");
        when(userRespository.save(any(User.class))).thenReturn(User.builder().id(1L).username("prueba").rol("USER").build());

        CreateUserResponse response = userService.createUser(request);

        assertThat(response.getUsername()).isEqualTo("prueba");
        assertThat(response.getRol()).isEqualTo("USER");
    }

    @Test
    void deberiaLanzarExcepcionSiElUsuarioYaExiste() {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("prueba");
        request.setRol("password");

        when(userRespository.findByUsername("prueba")).thenReturn(Optional.of(User.builder().id(1L).username("prueba").build()));

        assertThatThrownBy(() -> userService.createUser(request)).isInstanceOf(UsernameAlreadyExistsException.class);
    }
}
