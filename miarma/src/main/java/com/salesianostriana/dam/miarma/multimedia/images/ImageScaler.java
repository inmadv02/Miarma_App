package com.salesianostriana.dam.miarma.multimedia.images;

import com.salesianostriana.dam.miarma.error.tiposErrores.ImageProcessException;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.OutputStream;

public interface ImageScaler {


    public byte[] scale(byte[] image, int width, String type);

    public byte[] scale(byte[] image, int width);

    public BufferedImage scale(BufferedImage image, int width) throws ImageProcessException;

}
