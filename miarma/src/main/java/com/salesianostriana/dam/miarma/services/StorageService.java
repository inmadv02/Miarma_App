package com.salesianostriana.dam.miarma.services;

import io.github.techgnious.exception.ImageException;
import io.github.techgnious.exception.VideoException;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;


public interface StorageService {

    void init();

    String store(MultipartFile file);

    String store(byte[] file, String filename);

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);

    void deleteFile(String filename);

    void deleteAll();
}
