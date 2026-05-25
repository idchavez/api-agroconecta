package com.apiagroconecta.api_agroconecta.service;

import com.apiagroconecta.api_agroconecta.model.Cliente;
import com.apiagroconecta.api_agroconecta.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    //listar todos los clientes
    public List<Cliente>  findAllCustomers() {
        return clienteRepository.findAll();
    }
}
