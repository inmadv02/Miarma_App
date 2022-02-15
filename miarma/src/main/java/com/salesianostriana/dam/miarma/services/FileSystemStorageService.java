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
import org.springframework.mock.web.MockMultipartFile;
import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;

import org.springframework.core.io.Resource;
import org.imgscalr.Scalr;

import java.awt.image.BufferedImage;
import java.io.*;
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
        String fileName = StringUtils.getFilename(filename);
        try {
            Path file = load(fileName);
            Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new StorageException("Error al eliminar un fichero", e);
        }

    }


    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }


    public MultipartFile scaleImage(MultipartFile file, int size) throws IOException{


        String filename = StringUtils.cleanPath(file.getOriginalFilename());

        String extension = StringUtils.getFilenameExtension(filename);

        String finalName = filename.replace("."+extension,"");

        BufferedImage original = ImageIO.read(file.getInputStream());

        BufferedImage scaled = Scalr.resize(original, size);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        ImageIO.write(scaled, extension, baos);
        //baos.flush();

        return new MockMultipartFile(finalName, baos.toByteArray());

    }
}
