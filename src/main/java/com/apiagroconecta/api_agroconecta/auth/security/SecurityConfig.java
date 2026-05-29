package com.apiagroconecta.api_agroconecta.auth.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// @EnableWebSecurity: activa el módulo de seguridad web de Spring Security.
// Esta clase define el SecurityFilterChain: las reglas que determinan
// qué solicitudes pasan y cuáles se bloquean, y con qué condiciones.
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final AuthenticationProvider authenticationProvider;

    public SecurityConfig(JwtFilter jwtFilter,
                          AuthenticationProvider authenticationProvider) {
        this.jwtFilter = jwtFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
    .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configure(http))
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth

                        // Swagger: público durante el desarrollo.
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        // Endpoints de autenticación: públicos — son la puerta de entrada.
                        .requestMatchers("/api/v1/auth/**").permitAll()

                        // Solo ADMIN
                        .requestMatchers("/api/v1/usuarios/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/categorias/**", "/api/v1/productos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/categorias/**", "/api/v1/productos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/categorias/**", "/api/v1/productos/**").hasRole("ADMIN")

                        // Lectura para ADMIN y CLIENTE
                        .requestMatchers(HttpMethod.GET, "/api/v1/productos/**", "/api/v1/categorias/**").hasAnyRole("ADMIN", "CLIENTE")

                        // Pedidos
                        .requestMatchers("/api/v1/pedidos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/pedidos/**").hasRole("CLIENTE")

                        .requestMatchers("/api/v1/detalles/**").hasAnyRole("ADMIN", "CLIENTE")
                        // Todo lo demás requiere autenticación.
                        .anyRequest().authenticated()
                )

                // Registrar el AuthenticationProvider que usa la BD.
                .authenticationProvider(authenticationProvider)

                // Insertar JwtFilter antes del filtro estándar de usuario/contraseña.
                // Así, cada solicitud primero pasa por la validación del token JWT.
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}