package com.transport.file_service.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.transport.file_service.dto.FileResponseDTO;
import com.transport.file_service.exception.BadRequestException;

@Service
public class FileStorageService {

	@Value("${file.upload-dir}")
	private String uploadDir;

    public FileResponseDTO saveFile(MultipartFile file) {

        if (file.isEmpty()) {
            throw new BadRequestException("File is empty");
        }
        
        if (!file.getContentType().equals("application/pdf") &&
        	    !file.getContentType().startsWith("image/")) {
        	    throw new BadRequestException("Invalid file type");
        	}

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        try {
            Path path = Paths.get(uploadDir, fileName)
                    .toAbsolutePath()
                    .normalize();

            Files.createDirectories(path.getParent());

            Files.write(path, file.getBytes());

            //String fileUrl = path.toString().replace("\\", "/");
            String fileUrl = "http://localhost:8083/files/" + fileName;

            return new FileResponseDTO(fileName, fileUrl);

        } catch (IOException e) {
            throw new RuntimeException("Error saving file");
        }
    }
}