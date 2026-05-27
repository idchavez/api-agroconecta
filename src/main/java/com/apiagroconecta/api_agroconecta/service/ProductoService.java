package com.apiagroconecta.api_agroconecta.service;

import com.apiagroconecta.api_agroconecta.dto.request.ProductoRequestDTO;
import com.apiagroconecta.api_agroconecta.dto.response.ProductoResponseDTO;
import com.apiagroconecta.api_agroconecta.exception.ResourceNotFoundException;
import com.apiagroconecta.api_agroconecta.model.Categoria;
import com.apiagroconecta.api_agroconecta.model.Producto;
import com.apiagroconecta.api_agroconecta.repository.CategoriaRepository;
import com.apiagroconecta.api_agroconecta.repository.ProductoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService {
    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public ProductoService(ProductoRepository productoRepository,
                           CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> findAll() {
        return productoRepository.findAll().stream()
                .map(ProductoResponseDTO::desde)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductoResponseDTO findById(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));
        return ProductoResponseDTO.desde(producto);
    }

    @Transactional
    public ProductoResponseDTO save(ProductoRequestDTO dto) {
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("La categoría asignada no existe"));

        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        producto.setDescripcion(dto.getDescripcion());
        producto.setCantidad(dto.getCantidad() != null ? dto.getCantidad() : 0);
        producto.setStockMinimo(dto.getStockMinimo() != null ? dto.getStockMinimo() : 1);
        producto.setDescripcionLong(dto.getDescripcionLong());

        // Controlamos el JsonNode: si es nulo, creamos un objeto JSON vacío ({}) seguro
        if (dto.getDetalles() != null) {
            producto.setDetalles(dto.getDetalles());
        } else {
            producto.setDetalles(objectMapper.createObjectNode());
        }
        producto.setCategoria(categoria);
        producto.setActivo(true);

//        if (dto.getImagen() != null && !dto.getImagen().isEmpty()) {
//            String imagenesConcatenadas = dto.getImagen().stream()
//                    .map(imageStorageService::guardarBase64ALocal)
//                    .collect(Collectors.joining(","));
//            producto.setImagen(imagenesConcatenadas);
//        }

        Producto guardado = productoRepository.save(producto);
        //return convertirAResponseDTO(guardado);
        return ProductoResponseDTO.desde(guardado);
    }

    @Transactional
    public ProductoResponseDTO update(Long id, ProductoRequestDTO dto) {
        Producto productoExistente = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado para actualizar"));

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("La categoría asignada no existe"));

        productoExistente.setNombre(dto.getNombre());
        productoExistente.setPrecio(dto.getPrecio());
        productoExistente.setDescripcion(dto.getDescripcion());
        productoExistente.setCantidad(dto.getCantidad());
        productoExistente.setStockMinimo(dto.getStockMinimo());
        productoExistente.setDescripcionLong(dto.getDescripcionLong());
        productoExistente.setDetalles(dto.getDetalles());
        productoExistente.setCategoria(categoria);

        // Si se suben nuevas imágenes, se sobrescriben las anteriores
//        if (dto.getImagenesBase64() != null && !dto.getImagenesBase64().isEmpty()) {
//            String nuevasImagenesConcatenadas = dto.getImagenesBase64().stream()
//                    .map(imageStorageService::guardarBase64ALocal)
//                    .collect(Collectors.joining(","));
//            productoExistente.setImagen(nuevasImagenesConcatenadas);
//        }

        Producto actualizado = productoRepository.save(productoExistente);
        //return convertirAResponseDTO(actualizado);
        return ProductoResponseDTO.desde(productoRepository.save(productoExistente));
    }

    // Helper interno para reconstruir la lista de URLs desde el String plano mapeado
//    private ProductoResponseDTO convertirAResponseDTO(Producto producto) {
//        List<String> urlsPublicas = Collections.emptyList();
//
//        if (producto.getImagen() != null && !producto.getImagen().trim().isEmpty()) {
//            urlsPublicas = Arrays.stream(producto.getImagen().split(","))
//                    .map(imageStorageService::generarUrlPublica)
//                    .collect(Collectors.toList());
//        }
//
//        return ProductoResponseDTO.desde(producto);
//    }
}
