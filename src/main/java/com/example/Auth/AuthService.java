package com.example.Auth;


import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Jwt.JwtService;
import com.example.User.Role;
import com.example.User.User;
import com.example.User.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        String token = jwtService.generateToken(user);
        
        return AuthResponse.builder()
                .token(token)
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())
                .message("Login exitoso")
                .success(true)
                .build();
    }

    public AuthResponse register(RegisterRequest request) {
        // Validar que el usuario no exista
        if (userRepository.existsByUsername(request.getUsername())) {
            return AuthResponse.builder()
                    .message("El username ya está en uso")
                    .success(false)
                    .build();
        }
        
        if (userRepository.existsByEmail(request.getEmail())) {
            return AuthResponse.builder()
                    .message("El email ya está en uso")
                    .success(false)
                    .build();
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .nombres(request.getNombres())
                .apellidos(request.getApellidos())
                .role(request.getRole() != null ? request.getRole() : Role.ADMIN)
                .enabled(true)
                .build();

        userRepository.save(user);
        
        String token = jwtService.generateToken(user);
        
        return AuthResponse.builder()
                .token(token)
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())
                .message("Registro exitoso")
                .success(true)
                .build();
    }
}