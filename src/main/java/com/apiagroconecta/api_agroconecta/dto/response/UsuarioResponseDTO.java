package com.apiagroconecta.api_agroconecta.dto.response;

import com.apiagroconecta.api_agroconecta.auth.model.Rol;
import com.apiagroconecta.api_agroconecta.auth.model.Usuario;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsuarioResponseDTO {
    /*
    private Long clienteId;
    private String name;
    private String email;
    private boolean activo;
    private String telefono;
    private Rol rol;

    // 💡 La anotación clave: si la lista es NULL, Jackson NO la incluirá en el JSON
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<PedidoResponseDTO> pedidos;
    //Datos de Trabajadores
    private BigDecimal salario;
    private LocalDate fechaContratacion;
    private String area;
    private String turno;
    private String codigoEmpleado;


    public UsuarioResponseDTO() {

    }

    public static UsuarioResponseDTO convertir (Usuario usuario) {

        UsuarioResponseDTO dto = new UsuarioResponseDTO();

        // 1. Mapeo de campos comunes a TODOS los usuarios
        dto.clienteId = usuario.getId();
        dto.name = usuario.getNombres();
        dto.telefono = usuario.getTelefono();
        dto.email = usuario.getCorreo();
        dto.activo = usuario.isEstado();
        dto.rol = usuario.getRol();

        // 2. Mapeo condicional según el Rol de la Entidad
        if (usuario.getRol() == Rol.CUSTOMER) {
            // Datos exclusivos de Clientes


            if (usuario.getPedidos() != null) {
                dto.pedidos = usuario.getPedidos().stream()
                        .map(PedidoResponseDTO::convertir) // O 'new PedidoResponseDTO'
                        .collect(Collectors.toList());
            }

            // Forzamos NULL en los datos de trabajadores para que Jackson los oculte
            dto.salario = null;
            dto.fechaContratacion = null;
            dto.area = null;
            dto.turno = null;
            dto.codigoEmpleado = null;

        } else {
            // Datos exclusivos de Trabajadores (ADMIN / OPERATOR)
            dto.salario = usuario.getSalario();
            dto.fechaContratacion = usuario.getFechaContratacion();
            dto.area = usuario.getArea();
            dto.turno = usuario.getTurno();
            dto.codigoEmpleado = usuario.getCodigoEmpleado();

            // Forzamos NULL en los datos de clientes para que Jackson los oculte

            dto.pedidos = null;
        }

        return dto;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public List<PedidoResponseDTO> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<PedidoResponseDTO> pedidos) {
        this.pedidos = pedidos;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public LocalDate getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(LocalDate fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(String codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

     */
}
