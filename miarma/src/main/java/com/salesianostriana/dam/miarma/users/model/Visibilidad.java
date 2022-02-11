package com.salesianostriana.dam.miarma.users.model;

public enum Visibilidad {

    PUBLIC("Público"), PRIVATE("Privado");

    private final String texto;

    private Visibilidad(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;

    }
}
