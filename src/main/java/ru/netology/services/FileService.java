package ru.netology.services;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.dtos.FileNameRequest;
import ru.netology.dtos.FileResponse;
import ru.netology.entity.FileEntity;
import ru.netology.exceptions.FileProcessingError;
import ru.netology.repositories.FileRepository;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FileService {

    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }


    @Transactional
    public byte[] getFile(String fileName) {
        Optional<FileEntity> file = fileRepository.findByFileNameEquals(fileName);
        if (file.isPresent()) {
            return file.get().getFileHolder();
        } else {
            throw new FileProcessingError("Файл не найден");
        }
    }

    @Transactional
    public List<FileResponse> getFiles(int limit) {
        List<FileEntity> files = fileRepository.findAll();
        List<FileResponse> respond = new ArrayList<>(limit);

        if (!files.isEmpty()) {
            for (int i = 0; i < files.size(); i++) {
                if (i == limit) {
                    break;
                }
                respond.add(new FileResponse(files.get(i).getFileName(), files.get(i).getSize()));
            }
        }
        return respond;
    }

    @Transactional
    public void removeFile(String filename) {
        Optional<FileEntity> file = fileRepository.findByFileNameEquals(filename);
        if (file.isPresent()) {
            fileRepository.deleteById(file.get().getId());
        } else {
            throw new FileProcessingError("Файл не найден");
        }
    }

    @Transactional
    public void updateFile(String filename, FileNameRequest newName) {
        System.out.println(newName.name());
        Optional<FileEntity> file = fileRepository.findByFileNameEquals(filename);
        if (file.isPresent()) {
            fileRepository.updateFile(newName.name(), file.get().getId());
        } else {
            throw new FileProcessingError("Файл не найден");
        }
    }

    @Transactional
    public void addFile(MultipartFile file) {

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
