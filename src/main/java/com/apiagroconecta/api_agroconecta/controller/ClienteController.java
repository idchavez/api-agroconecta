package com.apiagroconecta.api_agroconecta.controller;


import com.apiagroconecta.api_agroconecta.dto.response.UsuarioResponseDTO;
import com.apiagroconecta.api_agroconecta.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(name = "/clientes")
public class ClienteController {

    //Realizamos la Inyeccion de dependencias del service
    private final UsuarioService usuarioService;

    @Autowired
    public ClienteController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    //Obtenemos todos los clientes
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> findAllCustomers() {
        return  ResponseEntity.ok(usuarioService.findAllCustomers());
    }

    //Creamos un nuevo cliente (Se registra el cliente)
//    @PostMapping
//    public ResponseEntity<ClienteResponseDTO> crearCliente(
//            @Valid @RequestBody ClienteRequestDTO clienteRequestDTO) {
//        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.save());
//    }

}
