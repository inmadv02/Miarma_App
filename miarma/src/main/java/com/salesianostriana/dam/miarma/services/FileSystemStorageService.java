package com.salesianostriana.dam.miarma.services;

import com.salesianostriana.dam.miarma.config.StorageProperties;
import com.salesianostriana.dam.miarma.error.tiposErrores.FileNotFoundException;
import com.salesianostriana.dam.miarma.error.tiposErrores.StorageException;
import com.salesianostriana.dam.miarma.utils.MediaTypeUrlResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements StorageService {


    private final Path rootLocation;


    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @PostConstruct
    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException exception){
            throw new StorageException("No se ha podido inicializar el directorio", exception);
        }

    }

    @Override
    public String store(MultipartFile file) {

        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        String newFilename = "";

        try {

            if (file.isEmpty())
                throw new StorageException("El fichero que ha subido está vacío");

            newFilename = filename;

            while(Files.exists(rootLocation.resolve(newFilename))) {

                String extension = StringUtils.getFilenameExtension(newFilename);
                String name = newFilename.replace("."+extension,"");

                String suffix = Long.toString(System.currentTimeMillis());
                suffix = suffix.substring(suffix.length()-6);

                newFilename = name + "_" + suffix + "." + extension;

            }

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, rootLocation.resolve(newFilename),
                        StandardCopyOption.REPLACE_EXISTING);
            }


        } catch (IOException exception) {
            throw new StorageException("Error en el almacenamiento del fichero: " + newFilename, exception);
        }

        return newFilename;

    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        }
        catch (IOException e) {
            throw new StorageException("Error al leer los ficheros almacenados", e);
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {

        try {
            Path file = load(filename);
            MediaTypeUrlResource resource = new MediaTypeUrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new FileNotFoundException(
                        "No se ha podido encontrar el fichero: " + filename);
            }
        }
        catch (MalformedURLException e) {
            throw new FileNotFoundException("No se ha podido encontrar el fichero: " + filename, e);
        }
    }

    @Override
    public void deleteFile(String filename) {
        // Hazlo marrana
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public Resource scaleImage(String filename) throws IOException{
        byte[] byteImg = Files.readAllBytes(Paths.get(filename));

        BufferedImage original = ImageIO.read(
                new ByteArrayInputStream(byteImg)
        );


        BufferedImage scaled = Scalr.resize(original, 512);


        OutputStream out = Files.newOutputStream(Paths.get(filename));

        ImageIO.write(scaled, "jpg", out);
    }
}
