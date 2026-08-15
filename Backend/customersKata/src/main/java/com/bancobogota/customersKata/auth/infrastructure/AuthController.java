package com.bancobogota.customersKata.auth.infrastructure;

import com.bancobogota.customersKata.auth.application.JwtService;
import com.bancobogota.customersKata.auth.application.dto.LoginRequest;
import com.bancobogota.customersKata.auth.application.dto.LoginResponse;
import com.bancobogota.customersKata.auth.domain.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserRespository userRespository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService  jwtService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        Optional<User> user = userRespository.findByUsername(loginRequest.getUsername());
        if(user.isEmpty() || user == null) {
            throw new RuntimeException("Credenciales inválidas");
        }
        if(!passwordEncoder.matches(loginRequest.getPassword(), user.get().getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        String token = jwtService.generateToken(user.get().getUsername());
        return ResponseEntity.ok(new LoginResponse(token));
    }
}
