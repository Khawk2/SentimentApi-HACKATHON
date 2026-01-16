package com.example.demo.service;

import com.example.demo.dto.MlServiceResponse;
import com.example.demo.dto.SentimentRequest;
import com.example.demo.dto.SentimentResponse;
import com.example.demo.model.SentimentAnalysis;
import com.example.demo.repository.SentimentAnalysisRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SentimentServiceTest {

    @Mock
    private SentimentAnalysisRepository repository;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private TranslationService translationService;

    @InjectMocks
    private SentimentService sentimentService;

    @BeforeEach
    void setUp() {
        sentimentService = new SentimentService(repository, restTemplate, translationService);

        ReflectionTestUtils.setField(
                sentimentService,
                "mlServiceUrl",
                "http://localhost:8000"
        );
    }

    @Test
    void analyzeSentiment_successful_returnsPositive() {
        // GIVEN
        SentimentRequest request = new SentimentRequest();
        request.setText("This is a great product");

        when(translationService.translateToSpanish(anyString()))
                .thenReturn("Este es un gran producto");

        MlServiceResponse mlResponse = new MlServiceResponse();
        mlResponse.setPrevision("Bueno");
        mlResponse.setProbabilidad(0.95);

        when(restTemplate.exchange(
                contains("/predict"),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(MlServiceResponse.class)
        )).thenReturn(ResponseEntity.ok(mlResponse));

        // WHEN
        SentimentResponse response = sentimentService.analyzeSentiment(request);

        // THEN
        assertNotNull(response);
        assertEquals("Positivo", response.getPrevision());
        assertEquals(0.95, response.getProbabilidad());

        // Verificar que se guardó en BD
        ArgumentCaptor<SentimentAnalysis> captor =
                ArgumentCaptor.forClass(SentimentAnalysis.class);

        verify(repository).save(captor.capture());

        SentimentAnalysis saved = captor.getValue();
        assertEquals("Positivo", saved.getPrevision());
        assertEquals("This is a great product", saved.getText());
    }
}
