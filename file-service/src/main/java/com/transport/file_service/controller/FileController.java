package com.transport.file_service.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.transport.file_service.dto.FileResponseDTO;
import com.transport.file_service.service.FileStorageService;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

    private final FileStorageService service;

    @PostMapping("/upload")
    public List<FileResponseDTO> uploadFiles(
            @RequestParam("pdf") MultipartFile pdf,
            @RequestParam("image") MultipartFile image) {

        FileResponseDTO pdfResponse = service.saveFile(pdf);
        FileResponseDTO imageResponse = service.saveFile(image);

        return List.of(pdfResponse, imageResponse);
    }
    
    @GetMapping("/{fileName}")
    public ResponseEntity<Resource> getFile(@PathVariable String fileName) throws IOException {

        Path path = Paths.get("uploads").resolve(fileName).normalize();

        Resource resource = (Resource) new UrlResource(path.toUri());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}