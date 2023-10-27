package ru.netology.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FileResponse{
    @JsonProperty("filename")
    private String fileName;
    @JsonProperty("size")
    private long size;
    @Override
    public String toString() {
        return "fileName: "+fileName+"\n"+"size: "+size+"\n";
    }
}

