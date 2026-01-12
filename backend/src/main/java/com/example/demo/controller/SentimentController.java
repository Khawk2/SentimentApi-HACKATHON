package com.example.demo.controller;

import com.example.demo.dto.SentimentRequest;
import com.example.demo.dto.SentimentResponse;
import com.example.demo.service.SentimentService;
import com.example.demo.service.StatsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SentimentController {

    private static final Logger log = LoggerFactory.getLogger(SentimentController.class);

    private final SentimentService sentimentService;
    private final StatsService statsService;

    @PostMapping("/sentiment")
    public ResponseEntity<SentimentResponse> analyzeSentiment(
            @Valid @RequestBody SentimentRequest request) {
        log.info("Recibida petición para analizar sentimiento");

        SentimentResponse response = sentimentService.analyzeSentiment(request);

        return ResponseEntity.ok(response);
    }

    //localhost:8080/api/stats
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStatistics() {
        log.info("Recibida la petición para obtener estadisticas");

        Map<String, Object> stats = statsService.getStatistics();
        return ResponseEntity.ok(stats);
    }

    //localhost:080/api/stats
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health(){
        return ResponseEntity.ok(Map.of("status", "UP", "service", "Sentiment Api"));
    }


}
