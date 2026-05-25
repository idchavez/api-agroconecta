package com.apiagroconecta.api_agroconecta.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Entity
@Table(name = "productos")
public class Productos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 150)
    private String nombre;
    @Column(nullable = false, precision = 10, scale = 2)
    private Double precio;
    @Column(columnDefinition = "TEXT")
    private String descripcion;
    private String[] imagen;
    private String detalles;
    private Integer cantidad;

    @Column(name = "tipoproducto", length = 100)
    private String tipoProducto;

    @Column(name = "fechadeingreso", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fechaDeIngreso;
    private Boolean activo;
    private Integer stockMinimo;
    private String descripcionLong;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", referencedColumnName = "id")
    private Integer categoriaId;

    public Productos() {
    }

    public Productos(Long id, String nombre, Double precio, String descripcion, String[] imagen, String detalles, Integer cantidad, String tipoProducto, LocalDateTime fechaDeIngreso, Boolean activo, Integer stockMinimo, String descripcionLong, Integer categoriaId) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.detalles = detalles;
        this.cantidad = cantidad;
        this.tipoProducto = tipoProducto;
        this.fechaDeIngreso = fechaDeIngreso;
        this.activo = activo;
        this.stockMinimo = stockMinimo;
        this.descripcionLong = descripcionLong;
        this.categoriaId = categoriaId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String[] getImagen() {
        return imagen;
    }

    public void setImagen(String[] imagen) {
        this.imagen = imagen;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public LocalDateTime getFechaDeIngreso() {
        return fechaDeIngreso;
    }

    public void setFechaDeIngreso(LocalDateTime fechaDeIngreso) {
        this.fechaDeIngreso = fechaDeIngreso;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Integer getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(Integer stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public String getDescripcionLong() {
        return descripcionLong;
    }

    public void setDescripcionLong(String descripcionLong) {
        this.descripcionLong = descripcionLong;
    }

    public Integer getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Integer categoriaId) {
        this.categoriaId = categoriaId;
    }
}
