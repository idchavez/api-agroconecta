package com.apiagroconecta.api_agroconecta.service;

import com.apiagroconecta.api_agroconecta.dto.request.ClienteRequestDTO;
import com.apiagroconecta.api_agroconecta.dto.request.TrabajadorRequestDTO;
import com.apiagroconecta.api_agroconecta.dto.response.UsuarioResponseDTO;
import com.apiagroconecta.api_agroconecta.model.Rol;
import com.apiagroconecta.api_agroconecta.model.Usuario;
import com.apiagroconecta.api_agroconecta.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // ==========================================
    // CLIENTE (CUSTOMER) CRUD OPERATIONS
    // ==========================================

    // listar todos los clientes activos
    public List<UsuarioResponseDTO> findAllCustomers() {
        return usuarioRepository.findByRol(Rol.CUSTOMER)
                .stream()
                .filter(Usuario::isEstado)
                .map(UsuarioResponseDTO::convertir)
                .collect(Collectors.toList());
    }

    // obtener cliente por ID
    public UsuarioResponseDTO findCustomerById(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null || usuario.getRol() != Rol.CUSTOMER || !usuario.isEstado()) {
            return null;
        }
        return UsuarioResponseDTO.convertir(usuario);
    }

    // crear un nuevo cliente
    public UsuarioResponseDTO saveCustomer(ClienteRequestDTO dto) {
        if (usuarioRepository.existsByCorreo(dto.getCorreo())) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }
        Usuario usuario = new Usuario();
        usuario.setNombres(dto.getNombres());
        usuario.setTelefono(dto.getTelefono());
        usuario.setCorreo(dto.getCorreo());
        usuario.setPassword(dto.getPassword()); // En producción, usar encriptación
        usuario.setRol(Rol.CUSTOMER);
        usuario.setEstado(true);

        Usuario guardado = usuarioRepository.save(usuario);
        return UsuarioResponseDTO.convertir(guardado);
    }

    // actualizar cliente existente
    public UsuarioResponseDTO updateCustomer(Long id, ClienteRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null || usuario.getRol() != Rol.CUSTOMER || !usuario.isEstado()) {
            return null;
        }

        if (!usuario.getCorreo().equalsIgnoreCase(dto.getCorreo()) && usuarioRepository.existsByCorreo(dto.getCorreo())) {
            throw new IllegalArgumentException("El correo ya está registrado por otro usuario");
        }

        usuario.setNombres(dto.getNombres());
        usuario.setTelefono(dto.getTelefono());
        usuario.setCorreo(dto.getCorreo());
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            usuario.setPassword(dto.getPassword());
        }

        Usuario guardado = usuarioRepository.save(usuario);
        return UsuarioResponseDTO.convertir(guardado);
    }

    // eliminación lógica de cliente
    public boolean deleteCustomer(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null || usuario.getRol() != Rol.CUSTOMER || !usuario.isEstado()) {
            return false;
        }
        usuario.setEstado(false);
        usuarioRepository.save(usuario);
        return true;
    }

    // ==========================================
    // TRABAJADOR (ADMIN/OPERATOR) CRUD OPERATIONS
    // ==========================================

    // listar todos los trabajadores activos
    public List<UsuarioResponseDTO> findAllWorkers() {
        List<Usuario> admins = usuarioRepository.findByRol(Rol.ADMIN);
        List<Usuario> operators = usuarioRepository.findByRol(Rol.OPERATOR);

        List<Usuario> todos = new java.util.ArrayList<>();
        todos.addAll(admins);
        todos.addAll(operators);

        return todos.stream()
                .filter(Usuario::isEstado)
                .map(UsuarioResponseDTO::convertir)
                .collect(Collectors.toList());
    }

    // obtener trabajador por ID
    public UsuarioResponseDTO findWorkerById(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null || (usuario.getRol() != Rol.ADMIN && usuario.getRol() != Rol.OPERATOR) || !usuario.isEstado()) {
            return null;
        }
        return UsuarioResponseDTO.convertir(usuario);
    }

    // crear un nuevo trabajador
    public UsuarioResponseDTO saveWorker(TrabajadorRequestDTO dto) {
        if (usuarioRepository.existsByCorreo(dto.getCorreo())) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }
        Usuario usuario = new Usuario();
        usuario.setNombres(dto.getNombres());
        usuario.setTelefono(dto.getTelefono());
        usuario.setCorreo(dto.getCorreo());
        usuario.setPassword(dto.getPassword()); // En producción, usar encriptación
        usuario.setRol(dto.getRol() != null ? dto.getRol() : Rol.OPERATOR);
        usuario.setEstado(true);

        // Campos específicos de trabajador
        usuario.setSalario(dto.getSalario());
        usuario.setFechaContratacion(dto.getFechaContratacion());
        usuario.setArea(dto.getArea());
        usuario.setTurno(dto.getTurno());
        usuario.setCodigoEmpleado(dto.getCodigoEmpleado());

        Usuario guardado = usuarioRepository.save(usuario);
        return UsuarioResponseDTO.convertir(guardado);
    }

    // actualizar trabajador existente
    public UsuarioResponseDTO updateWorker(Long id, TrabajadorRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null || (usuario.getRol() != Rol.ADMIN && usuario.getRol() != Rol.OPERATOR) || !usuario.isEstado()) {
            return null;
        }

        if (!usuario.getCorreo().equalsIgnoreCase(dto.getCorreo()) && usuarioRepository.existsByCorreo(dto.getCorreo())) {
            throw new IllegalArgumentException("El correo ya está registrado por otro usuario");
        }

        usuario.setNombres(dto.getNombres());
        usuario.setTelefono(dto.getTelefono());
        usuario.setCorreo(dto.getCorreo());
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            usuario.setPassword(dto.getPassword());
        }
        if (dto.getRol() != null) {
            usuario.setRol(dto.getRol());
        }

        // Campos específicos de trabajador
        usuario.setSalario(dto.getSalario());
        usuario.setFechaContratacion(dto.getFechaContratacion());
        usuario.setArea(dto.getArea());
        usuario.setTurno(dto.getTurno());
        usuario.setCodigoEmpleado(dto.getCodigoEmpleado());

        Usuario guardado = usuarioRepository.save(usuario);
        return UsuarioResponseDTO.convertir(guardado);
    }

    // eliminación lógica de trabajador
    public boolean deleteWorker(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null || (usuario.getRol() != Rol.ADMIN && usuario.getRol() != Rol.OPERATOR) || !usuario.isEstado()) {
            return false;
        }
        usuario.setEstado(false);
        usuarioRepository.save(usuario);
        return true;
    }
}
