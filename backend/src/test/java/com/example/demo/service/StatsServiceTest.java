package com.example.demo.service;

import com.example.demo.repository.SentimentAnalysisRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatsServiceTest {

    private SentimentAnalysisRepository repository;
    private StatsService statsService;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(SentimentAnalysisRepository.class);
        statsService = new StatsService(repository);
    }

    @Test
    void shouldReturnStatisticsWhenThereIsData() {
        // GIVEN
        Mockito.when(repository.count()).thenReturn(10L);
        Mockito.when(repository.countByPrevision("Positivo")).thenReturn(5L);
        Mockito.when(repository.countByPrevision("Negativo")).thenReturn(3L);
        Mockito.when(repository.countByPrevision("Neutro")).thenReturn(2L);

        // WHEN
        Map<String, Object> stats = statsService.getStatistics();

        // THEN
        assertEquals(10L, stats.get("total"));
        assertEquals(5L, stats.get("positivos"));
        assertEquals(3L, stats.get("negativos"));
        assertEquals(2L, stats.get("neutros"));

        assertEquals(50L, stats.get("porcentaje_positivos"));
        assertEquals(30L, stats.get("porcentaje_negativos"));
        assertEquals(20L, stats.get("porcentaje_neutros"));
    }

    @Test
    void shouldReturnZeroStatisticsWhenThereIsNoData() {
        // GIVEN
        Mockito.when(repository.count()).thenReturn(0L);

        // WHEN
        Map<String, Object> stats = statsService.getStatistics();

        // THEN
        assertEquals(0L, stats.get("total"));
        assertEquals(0, stats.get("positivos"));
        assertEquals(0, stats.get("negativos"));
        assertEquals(0, stats.get("neutros"));

        assertEquals(0.0, stats.get("porcentaje_positivos"));
        assertEquals(0.0, stats.get("porcentaje_negativos"));
        assertEquals(0.0, stats.get("porcentaje_neutros"));
    }
}
