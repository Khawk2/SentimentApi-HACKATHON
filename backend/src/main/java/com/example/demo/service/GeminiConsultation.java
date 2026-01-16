package com.example.demo.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GeminiConsultation {

    private static final Logger log = LoggerFactory.getLogger(GeminiConsultation.class);
    private static final String MODELO = "gemini-2.5-flash-lite";
    private final Client client;

    public GeminiConsultation(@Value("${GEMINI_KEY}") String apiKey) {
        this.client = new Client.Builder()
                .apiKey(apiKey)
                .build();
    }

    public String traducirAlEspanol(String texto) {
        if (texto == null || texto.isBlank()) {
            return texto;
        }

        log.debug("Traduciendo texto: {}", texto.substring(0, Math.min(50, texto.length())));

        String prompt = """
                Traduce el siguiente texto al español manteniendo el significado y el tono original.
                Si el texto ya está en español, devuélvelo tal cual.
                Responde únicamente con el texto traducido, sin explicaciones adicionales.

                Texto: "%s"
                """.formatted(texto);

        try {
            GenerateContentResponse response =
                    client.models.generateContent(MODELO, prompt, null);

            if (response != null && response.text() != null) {
                return response.text().trim();
            }

        } catch (Exception e) {
            log.error("Error al traducir con Gemini", e);
        }

        // fallback seguro
        return texto;
    }
}