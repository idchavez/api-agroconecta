package com.apiagroconecta.api_agroconecta.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public FilterRegistrationBean<CorsFilter> customCorsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Permitir envío de credenciales (esencial si usas cookies o headers de auth)
        config.setAllowCredentials(true);

        // El origen de tu frontend en GitHub Pages (sin barra al final)
        config.setAllowedOrigins(List.of("https://danielvega825.github.io"));

        // Permitir todos los headers
        config.setAllowedHeaders(List.of("*"));

        // Métodos permitidos, incluyendo OPTIONS explícitamente
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));

        // Aplicar a todas las rutas de la API
        source.registerCorsConfiguration("/**", config);

        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));

        // ESTO ES LA CLAVE: Ejecutar este filtro antes que cualquier filtro de Spring Security
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}