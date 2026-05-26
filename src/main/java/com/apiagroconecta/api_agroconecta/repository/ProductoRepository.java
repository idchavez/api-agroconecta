package com.apiagroconecta.api_agroconecta.repository;

import com.apiagroconecta.api_agroconecta.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
