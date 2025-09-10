package com.blumbit.compras_ventas.service.impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blumbit.compras_ventas.common.dto.file.FileDownloadResponse;
import com.blumbit.compras_ventas.common.dto.file.FileRequest;
import com.blumbit.compras_ventas.common.dto.file.FileResponse;
import com.blumbit.compras_ventas.service.spec.FileService;

import jakarta.annotation.PostConstruct;

@Service
public class FileServiceImpl implements FileService {

    @Value("${file.path}")
    private String filePath;

    private Path fileStorageLocation;

    @PostConstruct
    public void init() {
        try {
            this.fileStorageLocation = Paths.get(filePath).toAbsolutePath().normalize();
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception e) {
            throw new RuntimeException("No se encuentra o no se puede crear el directorio para almacenar los archivos");
        }
    }

    @Override
    public FileResponse createFile(FileRequest fileRequest) {
        MultipartFile file = fileRequest.getFile();
        String uniqueFileName = generateUniqueFileName(file.getOriginalFilename());
        try {
            Path targetLocation = fileStorageLocation.resolve(uniqueFileName);
            Files.copy(file.getInputStream(), targetLocation);
            return FileResponse.builder().filePath(uniqueFileName).build();
        } catch (Exception e) {
            throw new RuntimeException("Error al crear el archivo");
        }
    }

    @Override
    public File retrieveFile(FileResponse fileResponse) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileResponse.getFilePath()).normalize();
            if (!filePath.startsWith(this.fileStorageLocation)) {
                throw new RuntimeException("No se puede acceder al directorio");
            }
            File file = filePath.toFile();
            if (!file.exists()) {
                throw new RuntimeException("Archivo no encontrado");
            }
            return file;
        } catch (Exception e) {
            throw new RuntimeException("Error recuperando el archivo");
        }
    }

    @Override
    public FileDownloadResponse fileDownload(String filePath) {      
        try {
            FileResponse fileResponse = FileResponse.builder().filePath(filePath).build();
            File file = retrieveFile(fileResponse);
            Path path = Paths.get(file.getAbsolutePath());
            Resource resource = new UrlResource(path.toUri());
            if (!resource.exists() || !resource.isReadable()) {
                throw new RuntimeException("Archino no encontrado");
            }
            String contentType = "";
            try {
                contentType = Files.probeContentType(path);
            } catch (Exception e) {
                contentType = "application/octet-stream";
            }
            return FileDownloadResponse.builder()
                    .resource(resource)
                    .contentType(contentType)
                    .fileName(file.getName())
                    .build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String generateUniqueFileName(String originalFileName) {
        if (originalFileName == null) {
            originalFileName = "file";
        }
        String timestamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        return timestamp + "_" + uuid + "_" + originalFileName;
    }

}
