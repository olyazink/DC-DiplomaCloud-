package ru.netology.controller;


import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.dtos.LoginRequest;
import ru.netology.dtos.LoginResponse;
import ru.netology.dtos.FileNameRequest;
import ru.netology.dtos.FileResponse;
import ru.netology.entity.FileEntity;
import ru.netology.services.AuthService;
import ru.netology.services.FileService;


import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@AllArgsConstructor
@RequestMapping("/")
public class Controller {


    private final ModelMapper modelMapper;
    @Autowired
    public final FileService fileService;
    @Autowired
    private final AuthService authService;


    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/logout")
    public void logout(@RequestHeader("auth-token") @NotBlank String authToken) {
        authService.logout(authToken);
    }


    @GetMapping("/list")
    @ResponseBody
    public List<FileResponse> getList(@RequestHeader("auth-token") @NotBlank String authToken, @RequestParam("limit") Integer limit) {
        authService.checkToken(authToken);
        return fileService.getFiles(limit).stream().map(file -> modelMapper.map(file, FileResponse.class)).limit(limit)
                .collect(Collectors.toList());
    }

    @PostMapping("/file")
    public void fileUpload(@RequestHeader("auth-token") @NotBlank String authToken, MultipartFile file) {
        authService.checkToken(authToken);
        fileService.addFile(file);
    }

    @DeleteMapping("/file")
    public void fileRemove(@RequestHeader("auth-token") @NotBlank String authToken, @RequestParam("filename") String filename) {
        authService.checkToken(authToken);
        fileService.removeFile(filename);
    }

    @GetMapping("/file")
    public ResponseEntity<byte[]> getFile(@RequestHeader("auth-token") @NotBlank String authToken, @RequestParam("filename") String filename) {
        authService.checkToken(authToken);
        return ResponseEntity.ok().contentType(MediaType.MULTIPART_FORM_DATA).body(fileService.getFile(filename));
    }

    @PutMapping("/file")
    public void updateFile(@RequestHeader("auth-token") @NotBlank String authToken, @RequestParam("filename") String filename, @RequestBody FileNameRequest name) {
        authService.checkToken(authToken);
        fileService.updateFile(filename, name);
    }


}
