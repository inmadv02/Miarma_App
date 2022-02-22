package com.salesianostriana.dam.miarma.images;

import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ImagingOpException;
import java.io.*;
import java.util.Locale;

public class ScalrImageScaler implements ImageScaler{
    @Override
    public byte[] scale(byte[] image, int width, String type) {
        return new byte[0];
    }

    @Override
    public byte[] scale(byte[] image, int width) {
        return new byte[0];
    }

    @Override
    public OutputStream scale(InputStream inputStream, int width) {
        return null;
    }

    @Override
    public BufferedImage scale(BufferedImage image, int width) {
        return null;
    }


    /*@Override
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

        }catch (IOException ex){

        }

    }

    @Override
    public byte[] scale(byte[] image, int width) {
        return new byte[0];
    }

    @Override
    public OutputStream scale(InputStream inputStream, int width) {
        return null;
    }

    @Override
    public BufferedImage scale(BufferedImage image, int width) {
        try{
            return Scalr.resize(image, width);
        } catch (IllegalArgumentException exception){
            //
        } catch (ImagingOpException imagingOpException){
            //
        }

    }*/
}
