package com.apiagroconecta.api_agroconecta.repository;

import com.apiagroconecta.api_agroconecta.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
