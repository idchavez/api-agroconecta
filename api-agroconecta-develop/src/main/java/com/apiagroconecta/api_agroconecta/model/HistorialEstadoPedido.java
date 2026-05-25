package com.apiagroconecta.api_agroconecta.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "historial_estados_pedido")
public class HistorialEstadoPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "pedido_id", nullable = false)
    private Integer pedidoId;

    @Column(name = "estado_id", nullable = false)
    private Integer estadoId;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(columnDefinition = "TEXT")
    private String nota;

    public HistorialEstadoPedido() {
    }

    public HistorialEstadoPedido(Integer id, Integer pedidoId, Integer estadoId, LocalDateTime fecha, String nota) {
        this.id = id;
        this.pedidoId = pedidoId;
        this.estadoId = estadoId;
        this.fecha = fecha;
        this.nota = nota;
    }
}
