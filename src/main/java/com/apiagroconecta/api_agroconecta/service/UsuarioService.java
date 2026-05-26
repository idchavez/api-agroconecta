package com.apiagroconecta.api_agroconecta.service;

import com.apiagroconecta.api_agroconecta.dto.response.UsuarioResponseDTO;
import com.apiagroconecta.api_agroconecta.model.Rol;
import com.apiagroconecta.api_agroconecta.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    //listar todos los clientes
    public List<UsuarioResponseDTO> findAllCustomers() {
        return usuarioRepository.findByRol(Rol.CUSTOMER)
                .stream()
                .map(UsuarioResponseDTO::convertir)
                .collect(Collectors.toList());
    }

//    public ClienteResponseDTO clientePorId(Long id) {
//        Cliente cliente = clienteRepository.findById(id).orElse(null);
//        if(cliente == null) return null;
//        return ClienteResponseDTO.convertir(cliente);
//    }
}
