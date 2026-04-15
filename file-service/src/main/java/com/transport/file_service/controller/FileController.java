package com.transport.file_service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.transport.file_service.dto.FileResponseDTO;
import com.transport.file_service.service.FileStorageService;

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
}