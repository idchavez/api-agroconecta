package com.apiagroconecta.api_agroconecta.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "clientes")
public class Cliente extends Usuario {

    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos;



    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
}
