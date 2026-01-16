package com.example.demo.service;

import com.example.demo.dto.MlServiceResponse;
import com.example.demo.dto.SentimentRequest;
import com.example.demo.dto.SentimentResponse;
import com.example.demo.model.SentimentAnalysis;
import com.example.demo.repository.SentimentAnalysisRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
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
        // Inyectamos la URL privada sin tocar el código original
        ReflectionTestUtils.setField(sentimentService, "mlServiceUrl", "http://fake-url.com");
    }

    @Test
    @DisplayName("Debe analizar un texto Positivo correctamente (Mapeo Bueno -> Positivo)")
    void testAnalyzeSentiment_Positivo() {
        // --- GIVEN ---
        String textoOriginal = "I love this product";
        String textoTraducido = "Amo este producto";

        // Usamos Reflection para asignar el texto
        SentimentRequest request = new SentimentRequest();
        ReflectionTestUtils.setField(request, "text", textoOriginal);

        // Simulamos la respuesta del ML
        MlServiceResponse mlResponseMock = new MlServiceResponse();
        ReflectionTestUtils.setField(mlResponseMock, "prevision", "Bueno");
        ReflectionTestUtils.setField(mlResponseMock, "probabilidad", 0.98);

        when(translationService.translateToSpanish(textoOriginal)).thenReturn(textoTraducido);

        when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(MlServiceResponse.class)))
                .thenReturn(new ResponseEntity<>(mlResponseMock, HttpStatus.OK));

        // --- WHEN ---
        SentimentResponse resultado = sentimentService.analyzeSentiment(request);

        // --- THEN ---
        assertNotNull(resultado);

        // Leemos el resultado privado
        Object prevision = ReflectionTestUtils.getField(resultado, "prevision");
        Object probabilidad = ReflectionTestUtils.getField(resultado, "probabilidad");

        assertEquals("Positivo", prevision);
        assertEquals(0.98, probabilidad);

        verify(repository, times(1)).save(any(SentimentAnalysis.class));
    }

    @Test
    @DisplayName("Debe analizar un texto Negativo correctamente (Mapeo Malo -> Negativo)")
    void testAnalyzeSentiment_Negativo() {
        // --- GIVEN ---
        String texto = "Mala calidad";
        SentimentRequest request = new SentimentRequest();
        ReflectionTestUtils.setField(request, "text", texto);

        MlServiceResponse mlResponseMock = new MlServiceResponse();
        ReflectionTestUtils.setField(mlResponseMock, "prevision", "Malo");
        ReflectionTestUtils.setField(mlResponseMock, "probabilidad", 0.85);

        when(translationService.translateToSpanish(anyString())).thenReturn(texto);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(), eq(MlServiceResponse.class)))
                .thenReturn(new ResponseEntity<>(mlResponseMock, HttpStatus.OK));

        // --- WHEN ---
        SentimentResponse resultado = sentimentService.analyzeSentiment(request);

        // --- THEN ---
        Object prevision = ReflectionTestUtils.getField(resultado, "prevision");
        assertEquals("Negativo", prevision);
    }

    @Test
    @DisplayName("Debe analizar un texto Neutro correctamente (Mapeo Regular -> Neutro)")
    void testAnalyzeSentiment_Neutro() {
        // --- GIVEN ---
        String texto = "Normal";
        SentimentRequest request = new SentimentRequest();
        ReflectionTestUtils.setField(request, "text", texto);

        MlServiceResponse mlResponseMock = new MlServiceResponse();
        ReflectionTestUtils.setField(mlResponseMock, "prevision", "Regular");
        ReflectionTestUtils.setField(mlResponseMock, "probabilidad", 0.50);

        when(translationService.translateToSpanish(anyString())).thenReturn(texto);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(), eq(MlServiceResponse.class)))
                .thenReturn(new ResponseEntity<>(mlResponseMock, HttpStatus.OK));

        // --- WHEN ---
        SentimentResponse resultado = sentimentService.analyzeSentiment(request);

        // --- THEN ---
        Object prevision = ReflectionTestUtils.getField(resultado, "prevision");
        assertEquals("Neutro", prevision);
    }

    @Test
    @DisplayName("Debe manejar errores si el servicio de ML falla")
    void testAnalyzeSentiment_Error() {
        // --- GIVEN ---
        SentimentRequest request = new SentimentRequest();
        ReflectionTestUtils.setField(request, "text", "Error test");

        when(translationService.translateToSpanish(anyString())).thenReturn("Error test");

        // --- CORRECCIÓN AQUÍ ---
        // Usamos el constructor que solo recibe el status (sin cuerpo), para evitar el error de 'null'
        when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(), eq(MlServiceResponse.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));

        // --- WHEN & THEN ---
        assertThrows(RuntimeException.class, () -> {
            sentimentService.analyzeSentiment(request);
        });

        verify(repository, never()).save(any());
    }
}