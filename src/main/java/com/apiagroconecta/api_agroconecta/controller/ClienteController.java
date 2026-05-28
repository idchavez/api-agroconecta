package com.apiagroconecta.api_agroconecta.controller;

import com.apiagroconecta.api_agroconecta.dto.request.ClienteRequestDTO;
import com.apiagroconecta.api_agroconecta.dto.response.UsuarioResponseDTO;
import com.apiagroconecta.api_agroconecta.auth.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    /*
    private final UsuarioService usuarioService;

    @Autowired
    public ClienteController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Obtener todos los clientes activos
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> findAllCustomers() {
        return ResponseEntity.ok(usuarioService.findAllCustomers());
    }

    // Obtener un cliente por su ID
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> findCustomerById(@PathVariable Long id) {
        UsuarioResponseDTO customer = usuarioService.findCustomerById(id);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customer);
    }

    // Crear/Registrar un nuevo cliente
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crearCliente(@Valid @RequestBody ClienteRequestDTO dto) {
        try {
            UsuarioResponseDTO nuevoCliente = usuarioService.saveCustomer(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCliente);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // Actualizar un cliente existente
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> updateCliente(
            @PathVariable Long id,
            @Valid @RequestBody ClienteRequestDTO dto) {
        try {
            UsuarioResponseDTO clienteActualizado = usuarioService.updateCustomer(id, dto);
            if (clienteActualizado == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(clienteActualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // Eliminación lógica de un cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        boolean eliminado = usuarioService.deleteCustomer(id);
        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

     */
}
