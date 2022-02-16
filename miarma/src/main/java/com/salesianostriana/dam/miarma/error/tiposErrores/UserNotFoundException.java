package com.salesianostriana.dam.miarma.error.tiposErrores;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(UUID id, Class c){
        super(String.format("No se puede encontrar una entidad del tipo %s con ID: %s", c.getName(), id));
    }
}
