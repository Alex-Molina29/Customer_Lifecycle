package com.bancobogota.customersKata.auth.infrastructure;

import com.bancobogota.customersKata.auth.application.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain chain
    ) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        //System.out.println("Header recibido: " + header);

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

           // System.out.println("Token extraído: " + token); // TEMPORAL
            //System.out.println("¿Es válido?: " + jwtService.isTokenValid(token));

            if (jwtService.isTokenValid(token)) {
                String username = jwtService.extractUsername(token);
                var authToken = new UsernamePasswordAuthenticationToken(username, null, List.of());

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        chain.doFilter(request, response);
    }
}
