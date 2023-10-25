package ru.netology.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;

public record FileNameRequest(@JsonProperty("filename") String name) {
}
