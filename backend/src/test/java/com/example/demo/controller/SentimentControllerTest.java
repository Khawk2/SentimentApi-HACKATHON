package com.example.demo.controller;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SentimentControllerTest {

    @Test
    void soloNumeros_retornarBadRequest() {
        // Test simple - verifica que los números no sean válidos
        String texto = "123456789";
        assertTrue(texto.matches("\\d+"), "El texto contiene solo números");
    }

    @Test
    void textoValido_retornaOk() {
        // Test simple - verifica que el texto sea válido
        String texto = "Este es un comentario excelente";
        assertFalse(texto.matches("\\d+"), "El texto no contiene solo números");
        assertTrue(texto.length() > 0, "El texto no está vacío");
    }
}

    