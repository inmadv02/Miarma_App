package com.salesianostriana.dam.miarma.error.tiposErrores;

import java.awt.image.ImagingOpException;

public class ImageProcessException extends RuntimeException {

    public ImageProcessException(String mensaje, Exception ex) {
        super(mensaje, ex);
    }
}
