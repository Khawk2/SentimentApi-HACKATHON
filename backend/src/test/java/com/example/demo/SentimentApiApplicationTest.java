package com.example.demo;

import com.example.demo.service.GeminiConsultation;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class SentimentApiApplicationTest {

    @Mock
    private GeminiConsultation geminiConsultation;

    @Test
    void contextLoads() {
        // Test vacío: solo verifica que el contexto levante
    }
}
