package com.salesianostriana.dam.miarma.multimedia.images;

import com.salesianostriana.dam.miarma.error.tiposErrores.ImageProcessException;
import com.salesianostriana.dam.miarma.multimedia.images.ImageScaler;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ImagingOpException;
import java.io.*;

@Service
public class ScalrImageScaler implements ImageScaler {

    @Override
    public byte[] scale(byte[] image, int width, String type) {

        String outputType = (type.toLowerCase().equalsIgnoreCase("jpg") ||
                            type.toLowerCase().equalsIgnoreCase("png") ||
                            type.toLowerCase().equalsIgnoreCase("gif")  ? type : "png");

        try(InputStream inputStream = new ByteArrayInputStream(image)){

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();


            BufferedImage bufferedImage = ImageIO.read(inputStream);
            BufferedImage scaled = scale(bufferedImage, width);
            ImageIO.write(scaled, outputType, byteArrayOutputStream);

            return byteArrayOutputStream.toByteArray();

        } catch (IOException ex){
            throw new ImageProcessException("Ha habido un error al escalar la imagen", ex);
        }

    }


    @Override
    public byte[] scale(byte[] image, int width) {
        return new byte[0];
    }


    @Override
    public BufferedImage scale(BufferedImage image, int width) throws ImageProcessException {
        try{
            return Scalr.resize(image, width);
        } catch (IllegalArgumentException | ImagingOpException exception) {
            throw new ImageProcessException("Ha habido un error al escalar la imagen", exception);
        }

    }
}
