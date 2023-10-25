package ru.netology.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginRequest {
    @NotBlank
    private String login;
    @NotBlank
    private String password;
}
