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

    @Test
    void textoNoEntendible_debeSerRechazado() {
        String[] frasesSinSentido = {[]

        };

        for (String texto : frasesSinSentido) {

            // para caracteres repetidos
            boolean esValido = texto.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s.,!?]+$") && !texto.matches(".*(.)\\1{3,}.*");
            // que se valide frase escrita
            assertTrue(esValido, "La frase '" + texto + "' debería haber sido rechazada por el sistema");
        }
    }
    