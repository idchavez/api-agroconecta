package com.apiagroconecta.api_agroconecta.controller;

import com.apiagroconecta.api_agroconecta.dto.request.TrabajadorRequestDTO;
import com.apiagroconecta.api_agroconecta.dto.response.UsuarioResponseDTO;
import com.apiagroconecta.api_agroconecta.auth.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trabajadores")
public class TrabajadorController {
    /*
    private final UsuarioService usuarioService;

    @Autowired
    public TrabajadorController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Obtener todos los trabajadores activos
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> findAllWorkers() {
        return ResponseEntity.ok(usuarioService.findAllWorkers());
    }

    // Obtener un trabajador por su ID
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> findWorkerById(@PathVariable Long id) {
        UsuarioResponseDTO worker = usuarioService.findWorkerById(id);
        if (worker == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(worker);
    }

    // Crear un nuevo trabajador (ADMIN u OPERATOR)
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crearTrabajador(@Valid @RequestBody TrabajadorRequestDTO dto) {
        try {
            UsuarioResponseDTO nuevoTrabajador = usuarioService.saveWorker(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoTrabajador);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // Actualizar un trabajador existente
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> updateTrabajador(
            @PathVariable Long id,
            @Valid @RequestBody TrabajadorRequestDTO dto) {
        try {
            UsuarioResponseDTO trabajadorActualizado = usuarioService.updateWorker(id, dto);
            if (trabajadorActualizado == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(trabajadorActualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // Eliminación lógica de un trabajador
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrabajador(@PathVariable Long id) {
        boolean eliminado = usuarioService.deleteWorker(id);
        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }*/
}
