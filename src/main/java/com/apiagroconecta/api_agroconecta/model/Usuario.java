package com.apiagroconecta.api_agroconecta.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombres;

    @Column(nullable = false)
    private String telefono;

    @Column(unique = true, nullable = false)
    private String correo;

    @Column(nullable = false)
    private String password;


}
