package com.apiagroconecta.api_agroconecta.repository;

import com.apiagroconecta.api_agroconecta.model.Rol;
import com.apiagroconecta.api_agroconecta.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByEmail(String email);

    // Spring Data JPA genera automáticamente: SELECT * FROM usuarios WHERE rol = ?
    List<Usuario> findByRol(Rol rol);
}
