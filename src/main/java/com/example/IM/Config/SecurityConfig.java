package com.example.IM.Config;

import com.example.IM.Jwt.JwtAuthenticationFilter;
import com.example.IM.Jwt.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(request -> {
                var corsConfiguration = new org.springframework.web.cors.CorsConfiguration();
                corsConfiguration.setAllowedOrigins(java.util.Arrays.asList(
                    "http://localhost:3000",
                    "http://localhost:5173"
                ));
                corsConfiguration.setAllowedMethods(java.util.Arrays.asList(
                    "GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"
                ));
                corsConfiguration.setAllowedHeaders(java.util.Arrays.asList(
                    "Authorization",
                    "Content-Type",
                    "Accept",
                    "Origin",
                    "X-Requested-With"
                ));
                corsConfiguration.setExposedHeaders(java.util.Arrays.asList("Authorization"));
                corsConfiguration.setAllowCredentials(true);
                corsConfiguration.setMaxAge(3600L);
                return corsConfiguration;
            }))
            .csrf(csrf -> csrf.disable())
            .exceptionHandling(exception -> exception
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(auth -> auth
                // ==================== PÚBLICO ====================
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/public/**").permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // ==================== CATÁLOGOS (GET público) ====================
                // Estados
                .requestMatchers(HttpMethod.GET, "/api/estados/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/estados/**").hasAnyRole("ADMIN", "MANAGER")
                .requestMatchers(HttpMethod.PUT, "/api/estados/**").hasAnyRole("ADMIN", "MANAGER")
                .requestMatchers(HttpMethod.DELETE, "/api/estados/**").hasRole("ADMIN")

                // Áreas
                .requestMatchers(HttpMethod.GET, "/api/areas/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/areas/**").hasAnyRole("ADMIN", "MANAGER")
                .requestMatchers(HttpMethod.PUT, "/api/areas/**").hasAnyRole("ADMIN", "MANAGER")
                .requestMatchers(HttpMethod.DELETE, "/api/areas/**").hasRole("ADMIN")

                // Tipos de Equipo (GET público para dropdowns)
                .requestMatchers(HttpMethod.GET, "/api/tipos-equipo/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/tipos-equipo/**").hasAnyRole("ADMIN", "MANAGER")
                .requestMatchers(HttpMethod.PUT, "/api/tipos-equipo/**").hasAnyRole("ADMIN", "MANAGER")
                .requestMatchers(HttpMethod.DELETE, "/api/tipos-equipo/**").hasRole("ADMIN")

                // Accesorios
                .requestMatchers(HttpMethod.GET, "/api/accesorios/**").hasAnyRole("ADMIN", "MANAGER", "USER")
                .requestMatchers(HttpMethod.POST, "/api/accesorios/**").hasAnyRole("ADMIN", "MANAGER")
                .requestMatchers(HttpMethod.PUT, "/api/accesorios/**").hasAnyRole("ADMIN", "MANAGER")
                .requestMatchers(HttpMethod.DELETE, "/api/accesorios/**").hasRole("ADMIN")

                // ==================== EMPLEADOS ====================
                .requestMatchers(HttpMethod.GET, "/api/empleados/**").hasAnyRole("ADMIN", "MANAGER", "USER")
                .requestMatchers(HttpMethod.POST, "/api/empleados/**").hasAnyRole("ADMIN", "MANAGER")
                .requestMatchers(HttpMethod.PUT, "/api/empleados/**").hasAnyRole("ADMIN", "MANAGER")
                .requestMatchers(HttpMethod.DELETE, "/api/empleados/**").hasRole("ADMIN")

                // ==================== EQUIPOS ====================
                .requestMatchers(HttpMethod.GET, "/api/equipos/**").hasAnyRole("ADMIN", "MANAGER", "USER")
                .requestMatchers(HttpMethod.POST, "/api/equipos/**").hasAnyRole("ADMIN", "MANAGER")
                .requestMatchers(HttpMethod.PUT, "/api/equipos/**").hasAnyRole("ADMIN", "MANAGER")
                .requestMatchers(HttpMethod.DELETE, "/api/equipos/**").hasRole("ADMIN")

                // ==================== HISTORIAL ====================
                .requestMatchers(HttpMethod.GET, "/api/historial/**").hasAnyRole("ADMIN", "MANAGER", "USER")

                // ==================== ADMIN EXCLUSIVO ====================
                .requestMatchers("/api/admin/**").hasRole("ADMIN")

                // ==================== CUALQUIER OTRA ====================
                .anyRequest().authenticated()
            )
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}