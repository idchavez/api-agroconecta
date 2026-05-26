package com.apiagroconecta.api_agroconecta.dto.request;

import com.apiagroconecta.api_agroconecta.model.Rol;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TrabajadorRequestDTO extends UsuarioRequestDTO {


    @NotNull(message = "El salario es obligatorio")
    @Positive(message = "El salario debe ser un valor positivo")
    private BigDecimal salario;

    @NotNull(message = "La fecha de contratación es obligatoria")
    @PastOrPresent(message = "La fecha de contratación debe estar en el pasado o presente")
    private LocalDate fechaContratacion;

    @NotNull
    @NotBlank(message = "El área es obligatoria")
    private String area;


    @NotNull
    @NotBlank(message = "El turno es obligatorio")
    private String turno;


    @NotBlank(message = "El código de empleado es obligatorio")
    private String codigoEmpleado;

    public TrabajadorRequestDTO() {
        super();
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
}
