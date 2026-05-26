package com.apiagroconecta.api_agroconecta.dto.request;

import com.apiagroconecta.api_agroconecta.model.Rol;

public class OperatorRequestDTO extends TrabajadorRequestDTO {
    public OperatorRequestDTO() {
        super();
        this.setRol(Rol.OPERATOR);
    }
}
