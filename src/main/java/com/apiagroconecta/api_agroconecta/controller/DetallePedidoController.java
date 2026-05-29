package com.apiagroconecta.api_agroconecta.controller;

import com.apiagroconecta.api_agroconecta.dto.request.DetallePedidoRequestDTO;
import com.apiagroconecta.api_agroconecta.dto.response.DetallePedidoResponseDTO;
import com.apiagroconecta.api_agroconecta.service.DetallePedidoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/detalles")
public class DetallePedidoController {

    @Autowired
    private DetallePedidoService service;

    @GetMapping
    public ResponseEntity<List<DetallePedidoResponseDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetallePedidoResponseDTO> getById(@PathVariable Long id) {

        DetallePedidoResponseDTO dto = service.findById(id);

        if (dto == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<DetallePedidoResponseDTO> create(
            @RequestBody DetallePedidoRequestDTO dto) {

        DetallePedidoResponseDTO res = service.save(dto);

        if (res == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}