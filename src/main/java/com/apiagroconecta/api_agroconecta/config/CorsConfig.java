package com.apiagroconecta.api_agroconecta.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

// CORS: Cross-Origin Resource Sharing.
// Configurado para permitir peticiones seguras desde el frontend en GitHub Pages.
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // Permite solicitudes únicamente desde el dominio de tu frontend.
        // Los navegadores no validan rutas completas ni hashes (/#/), solo el origen.
        config.addAllowedOrigin("https://danielvega825.github.io");


        // Permite los métodos HTTP que usa el frontend.
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("OPTIONS");

        // Permite todos los headers, incluyendo Authorization: Bearer <token>
        config.addAllowedHeader("*");

        // Permite el envío de credenciales (cookies, headers de autenticación, etc.)
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // Aplica esta configuración a todas las rutas de la API.
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
