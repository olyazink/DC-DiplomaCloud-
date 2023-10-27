package ru.netology.services;


import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.dtos.FileNameRequest;
import ru.netology.entity.FileEntity;
import ru.netology.exceptions.FileProcessingError;
import ru.netology.repositories.FileRepository;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class FileService {

    private final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final FileRepository fileRepository;


    public byte[] getFile(String fileName) {
        logger.info("Getting the file..");
        Optional<FileEntity> file = fileRepository.findByFileNameEquals(fileName);
        if (file.isPresent()) {
            return file.get().getFileHolder();
        } else {
            throw new FileProcessingError("Файл не найден");
        }
    }


    public List<FileEntity> getFiles(int limit) {
        return fileRepository.findAll();
    }


    public void removeFile(String filename) {
        logger.info("Deleting file {} from cloud..", filename);
        Optional<FileEntity> file = fileRepository.findByFileNameEquals(filename);
        if (file.isPresent()) {
            fileRepository.deleteById(file.get().getId());
            logger.info("file {} deleted successful", filename);
        } else {
            throw new FileProcessingError("Файл не найден");
        }
    }


    public void updateFile(String filename, FileNameRequest newName) {
        logger.info("Updating file {} from cloud..", filename);
        System.out.println(newName.name());
        Optional<FileEntity> file = fileRepository.findByFileNameEquals(filename);
        if (file.isPresent()) {
            fileRepository.updateFile(newName.name(), file.get().getId());
            logger.info("file {} updated successful", filename);
        } else {
            throw new FileProcessingError("Файл не найден");
        }
    }


    public void addFile(MultipartFile file) {
        logger.info("Add file from cloud..");
        try {
            fileRepository.save(FileEntity.builder()
                    .fileName(file.getOriginalFilename())
                    .size(file.getSize())
                    .fileHolder(file.getInputStream().readAllBytes())
                    .build());
        } catch (IOException | RuntimeException e) {
            throw new FileProcessingError(e.getMessage());
        }
    }

}
