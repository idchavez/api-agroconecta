package com.apiagroconecta.api_agroconecta.service;

import com.apiagroconecta.api_agroconecta.dto.request.ProductoRequestDTO;
import com.apiagroconecta.api_agroconecta.dto.response.ProductoResponseDTO;
import com.apiagroconecta.api_agroconecta.model.Categoria;
import com.apiagroconecta.api_agroconecta.model.Producto;
import com.apiagroconecta.api_agroconecta.repository.CategoriaRepository;
import com.apiagroconecta.api_agroconecta.repository.ProductoRepository;
import com.apiagroconecta.api_agroconecta.service.storage.UploadImageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService {
    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final UploadImageService uploadImageService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    public ProductoService(ProductoRepository productoRepository,
                           CategoriaRepository categoriaRepository,
                           UploadImageService uploadImageService) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
        this.uploadImageService = uploadImageService;
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
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
        return ProductoResponseDTO.desde(producto);
    }

    @Transactional
    public ProductoResponseDTO save(ProductoRequestDTO dto) {
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("La categoría asignada no existe"));

        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        producto.setDescripcion(dto.getDescripcion());
        producto.setCantidad(dto.getCantidad() != null ? dto.getCantidad() : 0);
        producto.setStockMinimo(dto.getStockMinimo() != null ? dto.getStockMinimo() : 1);
        producto.setDescripcionLong(dto.getDescripcionLong());

        if (dto.getDetalles() != null) {
            producto.setDetalles(dto.getDetalles());
        } else {
            producto.setDetalles(objectMapper.createObjectNode());
        }
        producto.setCategoria(categoria);
        producto.setActivo(true);

        // Procesar y subir múltiples imágenes a GCP
        if (dto.getImagenes() != null && !dto.getImagenes().isEmpty()) {
            List<String> urls = new ArrayList<>();
            for (String img : dto.getImagenes()) {
                if (img == null || img.isBlank()) {
                    continue;
                }
                if (img.startsWith("http://") || img.startsWith("https://")) {
                    urls.add(img);
                } else {
                    try {
                        String url = uploadImageService.uploadBase64(img);
                        urls.add(url);
                    } catch (IOException e) {
                        throw new RuntimeException("Error al subir la imagen a GCP: " + e.getMessage(), e);
                    }
                }
            }
            producto.setImagenesFromList(urls);
        } else {
            producto.setImagen("");
        }

        Producto guardado = productoRepository.save(producto);
        return ProductoResponseDTO.desde(guardado);
    }

    @Transactional
    public ProductoResponseDTO update(Long id, ProductoRequestDTO dto) {
        Producto productoExistente = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado para actualizar"));

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("La categoría asignada no existe"));

        productoExistente.setNombre(dto.getNombre());
        productoExistente.setPrecio(dto.getPrecio());
        productoExistente.setDescripcion(dto.getDescripcion());
        productoExistente.setCantidad(dto.getCantidad());
        productoExistente.setStockMinimo(dto.getStockMinimo());
        productoExistente.setDescripcionLong(dto.getDescripcionLong());
        productoExistente.setDetalles(dto.getDetalles());
        productoExistente.setCategoria(categoria);

        // Procesar y subir nuevas imágenes (sobrescribiendo las anteriores)
        if (dto.getImagenes() != null && !dto.getImagenes().isEmpty()) {
            List<String> urls = new ArrayList<>();
            for (String img : dto.getImagenes()) {
                if (img == null || img.isBlank()) {
                    continue;
                }
                if (img.startsWith("http://") || img.startsWith("https://")) {
                    urls.add(img);
                } else {
                    try {
                        String url = uploadImageService.uploadBase64(img);
                        urls.add(url);
                    } catch (IOException e) {
                        throw new RuntimeException("Error al subir la imagen a GCP: " + e.getMessage(), e);
                    }
                }
            }
            productoExistente.setImagenesFromList(urls);
        } else {
            productoExistente.setImagen("");
        }

        Producto actualizado = productoRepository.save(productoExistente);
        return ProductoResponseDTO.desde(actualizado);
    }
}
