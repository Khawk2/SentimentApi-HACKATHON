package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SentimentRequest {

    @NotBlank(message = "El campo 'text' es obligatorio")
    @Size(min = 1, message = "El texto debe tener al menos 1 carácter")
    private String text;

}
