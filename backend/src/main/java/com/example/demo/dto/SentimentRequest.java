package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import jakarta.validation.constraints.Pattern;

@Data
public class SentimentRequest {

    @NotBlank(message = "El campo 'text' es obligatorio")
    @Size(min = 2, message = "El texto debe tener al menos 3 carácter")
    @Pattern(regexp = "^(?!.*(.)\\1{3,})[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s.,!?]+$",
            message = "Debe escribir un comentario válido en español")
    String text
)

}
