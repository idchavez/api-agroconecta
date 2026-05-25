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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;

    public Usuario() {
    }

    public Usuario(Long id,
                   String nombres,
                   String telefono,
                   String correo,
                   String password,
                   Rol rol) {
        this.id = id;
        this.nombres = nombres;
        this.telefono = telefono;
        this.correo = correo;
        this.password = password;
        this.rol = rol;
    }


}
