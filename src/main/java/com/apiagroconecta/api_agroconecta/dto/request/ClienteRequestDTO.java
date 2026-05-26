package com.apiagroconecta.api_agroconecta.dto.request;

import com.apiagroconecta.api_agroconecta.model.Rol;

public class ClienteRequestDTO extends UsuarioRequestDTO {

    public ClienteRequestDTO() {
        super();
        this.setRol(Rol.CUSTOMER);
    }


}
