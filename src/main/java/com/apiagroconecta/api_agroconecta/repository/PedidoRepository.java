package com.apiagroconecta.api_agroconecta.repository;

import com.apiagroconecta.api_agroconecta.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
