package ru.netology.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.netology.dtos.FileNameRequest;
import ru.netology.dtos.FileResponse;
import ru.netology.entity.FileEntity;
import ru.netology.exceptions.FileProcessingError;
import ru.netology.repositories.FileRepository;
import ru.netology.services.FileService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class FileServiceTest {

    @Mock
    FileRepository repository;
    @InjectMocks
    FileService fileService;

    @Test
    void getFile() {
        //arrange
        byte[] savedFile = {1, 2, 3};
        when(repository.findByFileNameEquals("file")).thenReturn(Optional.of(new FileEntity(1L, "file", 0L, savedFile)));
        //act
        byte[] file = fileService.getFile("file");
        //assert
        assertArrayEquals(savedFile, file);
    }

    @Test
    void getFiles() {
        //arrange
        List<FileResponse> respond = new ArrayList<>();
        respond.add(new FileResponse("first", 0L));
        respond.add(new FileResponse("second", 0L));
        respond.add(new FileResponse("third", 0L));
        List<FileEntity> request = new ArrayList<>();
        request.add(new FileEntity(1L, "first", 0L, null));
        request.add(new FileEntity(2L, "second", 0L, null));
        request.add(new FileEntity(3L, "third", 0L, null));
        request.add(new FileEntity(4L, "additional", 0L, null));
        when(repository.findAll()).thenReturn(request);
        //act
        List<FileResponse> result = fileService.getFiles(3);
        //assert
        assertThat(respond, is(result));
    }

    @Test
    void removeFile() {
        //arrange
        when(repository.findByFileNameEquals("file")).thenReturn(Optional.empty());
        //act-assert
        Assertions.assertThrows(FileProcessingError.class, () -> fileService.removeFile("file"));
    }

    @Test
    void updateFile() {
        //arrange
        FileNameRequest transfer = new FileNameRequest("newName");
        when(repository.findByFileNameEquals("file")).thenReturn(Optional.empty());
        //act-assert
        Assertions.assertThrows(FileProcessingError.class, () -> fileService.updateFile("file", transfer));
    }

}