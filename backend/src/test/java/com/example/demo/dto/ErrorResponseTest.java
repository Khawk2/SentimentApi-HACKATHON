package com.example.demo.dto;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class ErrorResponseTest {
    @Test
    void ErrorResponseTestConstructor() {
        // Establecer parámetros y argumentos
        LocalDateTime timestamp = LocalDateTime.now();
        int status = 400;
        String error = "Bad Request";
        String message= "El texto es obligatorio";
        String path = "/api/sentiment";

        // Crear objeto ErrorResponse
        ErrorResponse errorResponse = new ErrorResponse(
                timestamp,
                status,
                error,
                message,
                path
        );

        // Verificar guarda de datos
        assertEquals(timestamp, errorResponse.getTimestamp(),
                "La fecha/hora debe ser la de la creación de la ErrorResponse");
        assertEquals(400, errorResponse.getStatus(),
                "El código de error debe ser: 400");
        assertEquals("Bad Request", errorResponse.getError(),
                "El tipo de error debe ser: 'Bad Request'");
        assertEquals("El texto es obligatorio", errorResponse.getMessage(),
                "El mensaje debe ser: 'El texto es obligatorio'");
        assertEquals("/api/sentiment", errorResponse.getPath(),
                "La ruta debe ser: /api/sentiment");
    }

    @Test
    void ErrorResponseTestConstructorVacio() {
        // Crear objeto errorResponse sin argumentos
        ErrorResponse errorResponse = new ErrorResponse();

        // Verificar que el objeto errorResponse existe pero sin argumentos
        assertNotNull(errorResponse);
        assertNull(errorResponse.getTimestamp());
        assertEquals(0, errorResponse.getStatus());
        assertNull(errorResponse.getError());
        assertNull(errorResponse.getMessage());
        assertNull(errorResponse.getPath());
    }
}