package com.example.demo.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TranslationService {

    private static final Logger log = LoggerFactory.getLogger(TranslationService.class);

    private final GeminiConsultation geminiConsultation;
    private final TranslationCache cache;

    public String translateToSpanish(String text) {
        if (text == null || text.isBlank()) {
            return text;
        }

        // Verificar caché primero
        String cached = cache.get(text);
        if (cached != null) {
            log.debug("Traducción obtenida del cache para texto: {}", text.substring(0, Math.min(50, text.length())));
            return cached;
        }

        // Traducir usando Gemini
        String translated = geminiConsultation.traducirAlEspanol(text);
        
        if (translated == null || translated.isBlank()) {
            log.warn("Falló traducción, usando texto original");
            return text;
        }

        // Guardar en caché
        cache.put(text, translated);
        
        log.debug("Texto traducido y cacheado: {} -> {}", 
            text.substring(0, Math.min(50, text.length())),
            translated.substring(0, Math.min(50, translated.length())));

        return translated;
    }
}