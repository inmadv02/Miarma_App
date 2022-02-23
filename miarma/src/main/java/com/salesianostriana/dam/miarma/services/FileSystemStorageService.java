package com.salesianostriana.dam.miarma.services;

import com.salesianostriana.dam.miarma.config.StorageProperties;
import com.salesianostriana.dam.miarma.error.tiposErrores.FileNotFoundException;
import com.salesianostriana.dam.miarma.error.tiposErrores.StorageException;
import com.salesianostriana.dam.miarma.utils.MediaTypeUrlResource;
import io.github.techgnious.IVCompressor;
import io.github.techgnious.dto.IVSize;
import io.github.techgnious.dto.ImageFormats;
import io.github.techgnious.dto.ResizeResolution;
import io.github.techgnious.dto.VideoFormats;
import io.github.techgnious.exception.ImageException;
import io.github.techgnious.exception.VideoException;
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
        String newfilename = " ";

        try {
            if (file.isEmpty())
                throw new StorageException("El fichero que has subido está vacío");

            newfilename = createNameForFile(filename);

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, rootLocation.resolve(newfilename),
                        StandardCopyOption.REPLACE_EXISTING);
            }


        } catch (IOException exception) {
            throw new StorageException("Error en el almacenamiento del siguiente fichero: " + newfilename, exception);
        }

        return newfilename;

    }


    @Override
    public String store(byte[] file, String filename) {

        String newfilename = StringUtils.cleanPath(filename);

        if (filename.isEmpty()) {
            throw new StorageException("El fichero subido está vacío");
        }
        newfilename = createNameForFile(newfilename);

        try (InputStream inputStream = new ByteArrayInputStream(file)) {
            Files.copy(inputStream, rootLocation.resolve(newfilename),
                    StandardCopyOption.REPLACE_EXISTING);

        } catch(IOException ex) {
            throw new StorageException("Error en el almacenamiento del fichero: " + newfilename, ex);

        }

        return newfilename;

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


    private String createNameForFile(String filename) {
        String newFilename = filename;
        while(Files.exists(rootLocation.resolve(newFilename))) {

            String extension = StringUtils.getFilenameExtension(filename);
            String name = filename.replace("."+extension,"");

            String suffix = Double.toString(Math.floor(Math.random()*(1000000000-1)*1));

            newFilename = name + "_" + suffix + "." + extension;

        }
        return newFilename;
    }

/*
    public String scaleVideo(MultipartFile file) throws IOException, VideoException {

        String filename = createNameForFile(file);
        String extension = StringUtils.getFilenameExtension(filename);

        IVCompressor compressor = new IVCompressor();

        byte[] scaledVideo = compressor.reduceVideoSize(file.getBytes(), VideoFormats.MP4, ResizeResolution.R360P);

        Files.write(Paths.get("uploads/" + filename), scaledVideo);

        return filename;

    }*/
}
