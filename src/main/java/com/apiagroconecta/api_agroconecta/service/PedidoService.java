package com.apiagroconecta.api_agroconecta.service;

import com.apiagroconecta.api_agroconecta.dto.request.PedidoRequestDTO;
import com.apiagroconecta.api_agroconecta.dto.response.PedidoResponseDTO;
import com.apiagroconecta.api_agroconecta.model.*;
import com.apiagroconecta.api_agroconecta.repository.HistorialEstadoPedidoRepository;
import com.apiagroconecta.api_agroconecta.repository.PedidoRepository;
import com.apiagroconecta.api_agroconecta.repository.ProductoRepository;
import com.apiagroconecta.api_agroconecta.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;
    private final HistorialEstadoPedidoRepository historialRepository; // 1. Cambiado a final

    @Autowired
    public PedidoService(PedidoRepository pedidoRepository,
                         UsuarioRepository usuarioRepository,
                         ProductoRepository productoRepository,
                         HistorialEstadoPedidoRepository historialRepository) { // 2. Inyectado aquí
        this.pedidoRepository = pedidoRepository;
        this.usuarioRepository = usuarioRepository;
        this.productoRepository = productoRepository;
        this.historialRepository = historialRepository;
    }


    // Listar todos los pedidos
    public List<PedidoResponseDTO> findAll() {
        return pedidoRepository.findAll()
                .stream()
                .map(PedidoResponseDTO::convertir)
                .collect(Collectors.toList());
    }

    // Obtener un pedido por su ID
    public PedidoResponseDTO findById(Long id) {
        return pedidoRepository.findById(id)
                .map(PedidoResponseDTO::convertir)
                .orElse(null);
    }

    // Listar todos los pedidos de un cliente específico
    public List<PedidoResponseDTO> findByClienteId(Long clienteId) {
        Usuario cliente = usuarioRepository.findById(clienteId).orElse(null);
        if (cliente == null || cliente.getRol() != Rol.CUSTOMER) {
            throw new IllegalArgumentException("El ID proporcionado no pertenece a un cliente válido");
        }
        
        return pedidoRepository.findAll()
                .stream()
                .filter(p -> p.getCliente() != null && p.getCliente().getId().equals(clienteId))
                .map(PedidoResponseDTO::convertir)
                .collect(Collectors.toList());
    }

    // Crear un nuevo pedido con sus detalles y auditoría inicial
    @Transactional
    public PedidoResponseDTO save(PedidoRequestDTO dto) {
        Usuario cliente = usuarioRepository.findById(dto.getClienteId()).orElse(null);
        if (cliente == null || cliente.getRol() != Rol.CUSTOMER || !cliente.isEstado()) {
            throw new IllegalArgumentException("Cliente no encontrado o inactivo");
        }

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setCreadoAt(LocalDateTime.now());
        pedido.setEstadoActual(dto.getEstado() != null ? dto.getEstado() : EstadoPedido.PENDIENTE);
        pedido.setTotal(BigDecimal.ZERO);

        List<DetallePedido> detalles = new ArrayList<>();
        BigDecimal totalAcumulado = BigDecimal.ZERO;

        for (PedidoRequestDTO.DetalleItemRequestDTO item : dto.getDetalles()) {
            Producto producto = productoRepository.findById(item.getProductoId()).orElse(null);
            if (producto == null || !producto.getActivo()) {
                throw new IllegalArgumentException("Producto ID " + item.getProductoId() + " no encontrado o inactivo");
            }
            if (producto.getCantidad() < item.getCantidad()) {
                throw new IllegalArgumentException("Stock insuficiente para el producto: " + producto.getNombre());
            }

            // Descontar stock
            producto.setCantidad(producto.getCantidad() - item.getCantidad());
            productoRepository.save(producto);

            DetallePedido detalle = new DetallePedido();
            detalle.setPedido(pedido);
            detalle.setProducto(producto);
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUni(producto.getPrecio());

            detalles.add(detalle);

            BigDecimal subtotal = producto.getPrecio().multiply(BigDecimal.valueOf(item.getCantidad()));
            totalAcumulado = totalAcumulado.add(subtotal);
        }

        pedido.setDetalles(detalles);
        pedido.setTotal(totalAcumulado);

        // 1. Guardamos el pedido limpio
        pedido.setHistorial(new ArrayList<>());
        Pedido guardado = pedidoRepository.save(pedido);

        // 2. Creamos la auditoría asignando el objeto pedido directamente
        HistorialEstadoPedido historial = new HistorialEstadoPedido();
        historial.setPedido(guardado); // Ahora JPA sí leerá este objeto para el insert
        historial.setEstadoId(guardado.getEstadoActual());
        historial.setFecha(LocalDateTime.now());
        historial.setNota("Creación inicial del pedido");

        // 3. Persistimos el historial de forma explícita
        historialRepository.save(historial);

        // 4. Agregamos a la lista en memoria para estructurar el DTO de salida
        guardado.getHistorial().add(historial);

        return PedidoResponseDTO.convertir(guardado);
    }


        // Actualizar el estado de un pedido y registrar en el historial
    @Transactional
    public PedidoResponseDTO updateEstado(Long id, EstadoPedido nuevoEstado, String nota) {
        Pedido pedido = pedidoRepository.findById(id).orElse(null);
        if (pedido == null) {
            return null;
        }

        pedido.setEstadoActual(nuevoEstado);

        HistorialEstadoPedido historial = new HistorialEstadoPedido();
        historial.setPedido(pedido);
        historial.setEstadoId(nuevoEstado);
        historial.setFecha(LocalDateTime.now());
        historial.setNota(nota != null ? nota : "Cambio de estado a " + nuevoEstado.name());

        pedido.getHistorial().add(historial);

        Pedido guardado = pedidoRepository.save(pedido);
        return PedidoResponseDTO.convertir(guardado);
    }

    // Actualizar detalles y total del pedido
    @Transactional
    public PedidoResponseDTO update(Long id, PedidoRequestDTO dto) {
        Pedido pedido = pedidoRepository.findById(id).orElse(null);
        if (pedido == null) {
            return null;
        }

        // Devolver stock original
        for (DetallePedido detalle : pedido.getDetalles()) {
            Producto producto = detalle.getProducto();
            producto.setCantidad(producto.getCantidad() + detalle.getCantidad());
            productoRepository.save(producto);
        }

        // Limpiar detalles anteriores
        pedido.getDetalles().clear();

        BigDecimal totalAcumulado = BigDecimal.ZERO;
        List<DetallePedido> nuevosDetalles = new ArrayList<>();

        for (PedidoRequestDTO.DetalleItemRequestDTO item : dto.getDetalles()) {
            Producto producto = productoRepository.findById(item.getProductoId()).orElse(null);
            if (producto == null || !producto.getActivo()) {
                throw new IllegalArgumentException("Producto ID " + item.getProductoId() + " no encontrado o inactivo");
            }
            if (producto.getCantidad() < item.getCantidad()) {
                throw new IllegalArgumentException("Stock insuficiente para el producto: " + producto.getNombre());
            }

            // Descontar nuevo stock
            producto.setCantidad(producto.getCantidad() - item.getCantidad());
            productoRepository.save(producto);

            DetallePedido detalle = new DetallePedido();
            detalle.setPedido(pedido);
            detalle.setProducto(producto);
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUni(producto.getPrecio());

            nuevosDetalles.add(detalle);

            BigDecimal subtotal = producto.getPrecio().multiply(BigDecimal.valueOf(item.getCantidad()));
            totalAcumulado = totalAcumulado.add(subtotal);
        }

        pedido.getDetalles().addAll(nuevosDetalles);
        pedido.setTotal(totalAcumulado);

        // Registro en el historial
        HistorialEstadoPedido historial = new HistorialEstadoPedido();
        historial.setPedido(pedido);
        historial.setEstadoId(pedido.getEstadoActual());
        historial.setFecha(LocalDateTime.now());
        historial.setNota("Actualización de los artículos del pedido");
        pedido.getHistorial().add(historial);

        Pedido guardado = pedidoRepository.save(pedido);
        return PedidoResponseDTO.convertir(guardado);
    }

    // Cancelación lógica (devolviendo stock)
//    @Transactional
//    public boolean delete(Long id) {
//        Pedido pedido = pedidoRepository.findById(id).orElse(null);
//        if (pedido == null || pedido.getEstadoActual() == EstadoPedido.CANCELADA) {
//            return false;
//        }
//
//        // Devolver stock
//        for (DetallePedido detalle : pedido.getDetalles()) {
//            Producto producto = detalle.getProducto();
//            producto.setCantidad(producto.getCantidad() + detalle.getCantidad());
//            productoRepository.save(producto);
//        }
//
//        pedido.setEstadoActual(EstadoPedido.CANCELADA);
//
//        HistorialEstadoPedido historial = new HistorialEstadoPedido();
//        historial.setPedido(pedido);
//        historial.setEstadoId(EstadoPedido.CANCELADA);
//        historial.setFecha(LocalDateTime.now());
//        historial.setNota("Pedido cancelado (Borrado lógico)");
//        pedido.getHistorial().add(historial);
//
//        pedidoRepository.save(pedido);
//        return true;
//    }
}
