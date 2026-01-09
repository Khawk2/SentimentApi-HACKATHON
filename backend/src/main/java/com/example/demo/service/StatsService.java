package com.example.demo.service;

import com.example.demo.repository.SentimentAnalysisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatsService {

    private final SentimentAnalysisRepository repository;

    public Map<String, Object> getStatistics(){
        long total = repository.count();
        long positivos = repository.countByPrevision("Positivo");
        long negativos = repository.countByPrevision("Negativo");
        long neutros = repository.countByPrevision("Neutro");

        Map<String, Object> stats = new HashMap<>();
        stats.put("total", total);

        if (total > 0) {
            stats.put("positivos", positivos);
            stats.put("negativos", negativos);
            stats.put("neutros", neutros);
            stats.put("porcentaje_positivos", (positivos * 100) / total);
            stats.put("porcentaje_negativos", (negativos * 100) / total);
            stats.put("porcentaje_neutros", (neutros * 100) / total);
        } else {
            stats.put("positivos", 0);
            stats.put("negativos", 0);
            stats.put("neutros", 0);
            stats.put("porcentaje_positivos", 0.0);
            stats.put("porcentaje_negativos", 0.0);
            stats.put("porcentaje_neutros", 0.0);
        }
        return stats;
    }
}
