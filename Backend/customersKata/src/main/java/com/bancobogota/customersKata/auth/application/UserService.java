package com.bancobogota.customersKata.auth.application;

import com.bancobogota.customersKata.auth.application.dto.request.CreateUserRequest;
import com.bancobogota.customersKata.auth.application.dto.response.CreateUserResponse;
import com.bancobogota.customersKata.auth.domain.User;
import com.bancobogota.customersKata.auth.domain.exception.UsernameAlreadyExistsException;
import com.bancobogota.customersKata.auth.infrastructure.UserRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRespository userRespository;
    private final PasswordEncoder passwordEncoder;

    public CreateUserResponse createUser(CreateUserRequest createUserRequest) throws UsernameAlreadyExistsException {
        if(userRespository.findByUsername(createUserRequest.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException(createUserRequest.getUsername());
        }

        User userRegistered = User.builder()
                .username(createUserRequest.getUsername())
                .password(passwordEncoder.encode(createUserRequest.getPassword()))
                .rol(createUserRequest.getRol())
                .build();

        User saved = userRespository.save(userRegistered);

        return new CreateUserResponse(saved.getId(), saved.getUsername(), saved.getRol());
    }
}
