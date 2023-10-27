package ru.netology.service;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
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
import java.util.stream.Collectors;

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
        List<FileEntity> respond = new ArrayList<>();
        respond.add(new FileEntity(1L, "first", 0L, null));
        respond.add(new FileEntity(2L, "second", 0L, null));
        respond.add(new FileEntity(3L, "third", 0L, null));
        respond.add(new FileEntity(4L, "additional", 0L, null));
        when(repository.findAll()).thenReturn(respond);
        //act
        List<FileEntity> result = repository.findAll();
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