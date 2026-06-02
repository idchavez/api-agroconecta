package com.apiagroconecta.api_agroconecta.service.storage;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

@Configuration
public class GcpStorageConfig {

    @Bean
    public Storage storage(
            @Value("${spring.cloud.gcp.credentials.location:}") String gcpConfigFile) throws IOException {

        GoogleCredentials credentials;
        String base64Credentials = System.getenv("GCP_CREDENTIALS_BASE64");

        // 1. Intentar por Base64 (Producción)
        // Agregamos una expresión regular básica para validar que parezca una cadena Base64 válida
        if (base64Credentials != null && !base64Credentials.isBlank() && !base64Credentials.contains("?")) {
            try {
                // El decodificador MIME es más robusto y tolera saltos de línea o espacios accidentales
                byte[] decodedBytes = Base64.getMimeDecoder().decode(base64Credentials.trim());
                credentials = GoogleCredentials.fromStream(new ByteArrayInputStream(decodedBytes));
                System.out.println("GCP Storage: Inicializado correctamente usando la variable Base64.");
            } catch (IllegalArgumentException e) {
                System.err.println("GCP Storage Error: La variable GCP_CREDENTIALS_BASE64 tiene caracteres inválidos. Pasando a modo archivo local.");
                credentials = loadFromFile(gcpConfigFile);
            }
        } else {
            // 2. Intentar por archivo local (Desarrollo)
            credentials = loadFromFile(gcpConfigFile);
        }

        return StorageOptions.newBuilder()
                .setCredentials(credentials)
                .build()
                .getService();
    }

    private GoogleCredentials loadFromFile(String gcpConfigFile) throws IOException {
        if (gcpConfigFile == null || gcpConfigFile.isBlank()) {
            throw new IllegalArgumentException(
                    "Error de configuración de GCP: No se encontró una variable 'GCP_CREDENTIALS_BASE64' válida ni una ruta en 'spring.cloud.gcp.credentials.location'"
            );
        }

        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource(gcpConfigFile);

        if (!resource.exists()) {
            throw new IOException("El archivo de credenciales de GCP no existe en la ruta especificada: " + gcpConfigFile);
        }

        System.out.println("GCP Storage: Inicializado correctamente usando archivo local: " + gcpConfigFile);
        return GoogleCredentials.fromStream(resource.getInputStream());
    }
}
