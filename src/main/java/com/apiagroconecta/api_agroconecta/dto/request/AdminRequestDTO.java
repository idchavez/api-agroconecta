package com.apiagroconecta.api_agroconecta.dto.request;

import com.apiagroconecta.api_agroconecta.auth.model.Rol;

public class AdminRequestDTO extends TrabajadorRequestDTO {

    public AdminRequestDTO() {
        super();
        this.setRol(Rol.ADMIN);
    }
}
