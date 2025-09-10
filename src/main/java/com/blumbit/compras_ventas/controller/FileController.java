package com.blumbit.compras_ventas.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blumbit.compras_ventas.common.dto.file.FileDownloadResponse;
import com.blumbit.compras_ventas.common.dto.file.FileRequest;
import com.blumbit.compras_ventas.common.dto.file.FileResponse;
import com.blumbit.compras_ventas.service.spec.FileService;

import lombok.RequiredArgsConstructor;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @GetMapping
    public ResponseEntity<Resource> retrieveFile(@RequestParam String filePath) {
        FileDownloadResponse downloadResponse = fileService.fileDownload(filePath);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(downloadResponse.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment: filename=" + downloadResponse.getFileName())
                .body(downloadResponse.getResource());
    }

    @PostMapping
    public FileResponse uploadFile(@ModelAttribute FileRequest fileRequest) {
        return fileService.createFile(fileRequest);
    }
    
}
