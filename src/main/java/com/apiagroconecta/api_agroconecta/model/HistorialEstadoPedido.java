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
    private Integer pedido;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_id", nullable = false)
    private EstadoPedido estado;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(columnDefinition = "TEXT")
    private String nota;

    public HistorialEstadoPedido() {
    }

    public HistorialEstadoPedido(Integer id, Integer pedidoId, EstadoPedido estadoId, LocalDateTime fecha, String nota) {
        this.id = id;
        this.pedido = pedidoId;
        this.estado = estadoId;
        this.fecha = fecha;
        this.nota = nota;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPedidoId() {
        return pedido;
    }

    public void setPedidoId(Integer pedidoId) {
        this.pedido = pedidoId;
    }

    public EstadoPedido getEstadoId() {
        return estado;
    }

    public void setEstadoId(EstadoPedido estadoId) {
        this.estado = estadoId;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }
}
