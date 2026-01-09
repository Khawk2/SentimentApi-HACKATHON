package com.example.demo.service;

import com.example.demo.dto.MlServiceResponse;
import com.example.demo.dto.SentimentRequest;
import com.example.demo.dto.SentimentResponse;
import com.example.demo.model.SentimentAnalysis;
import com.example.demo.repository.SentimentAnalysisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class SentimentService {

    private final SentimentAnalysisRepository repository;
    private final RestTemplate restTemplate;

    @Value("${ml.service.url:http://localhost:8000}")
    private String mlServiceUrl;

    public SentimentResponse analyzeSentiment(SentimentRequest request) {
        log.info("Analizando sentimiento para texto: {}", request.getText().substring(0, Math.min(50, request.getText().length())));

        try {
            log.debug("Enviando petición a ML service: {}", mlServiceUrl + "/predict");
            log.debug("Body de la petición: text={}", request.getText());

            // Preparar headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(java.util.Collections.singletonList(MediaType.APPLICATION_JSON));

            // Crear el body como Map para serialización JSON
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("text", request.getText());

            // Crear HttpEntity con headers y body
            HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

            // Llamar al servicio de ML usando RestTemplate
            ResponseEntity<MlServiceResponse> response = restTemplate.exchange(
                    mlServiceUrl + "/predict",
                    HttpMethod.POST,
                    entity,
                    MlServiceResponse.class
            );

            MlServiceResponse mlResponse = response.getBody();

            if (mlResponse == null) {
                throw new RuntimeException("No se recibió respuesta del servicio de ML");
            }

            // Mapear las etiquetas del ML service (Bueno/Malo/Regular) a las esperadas por el sistema (Positivo/Negativo/Neutro)
            String mappedPrevision = mapSentimentLabel(mlResponse.getPrevision());

            // Guardar en base de datos
            SentimentAnalysis analysis = new SentimentAnalysis();
            analysis.setText(request.getText());
            analysis.setPrevision(mappedPrevision);
            analysis.setProbabilidad(mlResponse.getProbabilidad());
            repository.save(analysis);

            log.info("Predicción guardada: {} (probabilidad: {})",
                    mappedPrevision, mlResponse.getProbabilidad());

            return new SentimentResponse(
                    mappedPrevision,
                    mlResponse.getProbabilidad()
            );

        } catch (Exception e) {
            log.error("Error al analizar sentimiento: {}", e.getMessage(), e);
            throw new RuntimeException("Error al procesar el análisis de sentimiento: " + e.getMessage(), e);
        }
    }

    /**
     * Mapea las etiquetas del ML service a las etiquetas esperadas por el sistema.
     * ML service retorna: "Bueno", "Malo", "Regular"
     * Sistema espera: "Positivo", "Negativo", "Neutro"
     */
    private String mapSentimentLabel(String mlLabel) {
        if (mlLabel == null) {
            return "Neutro";
        }
        return switch (mlLabel.trim()) {
            case "Bueno" -> "Positivo";
            case "Malo" -> "Negativo";
            case "Regular" -> "Neutro";
            default -> {
                log.warn("Etiqueta desconocida del ML service: {}, usando 'Neutro' por defecto", mlLabel);
                yield "Neutro";
            }
        };
    }
}
