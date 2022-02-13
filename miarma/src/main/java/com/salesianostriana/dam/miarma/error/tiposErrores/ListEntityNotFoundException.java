package com.salesianostriana.dam.miarma.error.tiposErrores;

public class ListEntityNotFoundException extends RuntimeException {

    public ListEntityNotFoundException(Class c){
        super(String.format("No se pueden encontrar elementos del tipo %s", c.getName()));
    }
}