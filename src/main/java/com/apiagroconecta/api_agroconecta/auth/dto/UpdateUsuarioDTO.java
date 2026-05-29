package com.apiagroconecta.api_agroconecta.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdateUsuarioDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Size(min = 6, message = "La contraseña debe tener mínimo 6 caracteres")
    private String password;

    // Constructor vacío
    public UpdateUsuarioDTO() {
    }

    // Constructor opcional
    public UpdateUsuarioDTO(String nombre, String password) {
        this.nombre = nombre;
        this.password = password;
    }

    // Getters y Setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}