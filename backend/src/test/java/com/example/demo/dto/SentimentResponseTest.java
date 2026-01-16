package com.example.demo.dto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SentimentResponseTest {
    @Test
    void ResultadoAnalisisSentimiento() {
        //Test simple - Verifica que se obtiene el resultado del análisis del sentimiento
        //Analizar texto del usuario y obtener análisis del sentimiento
        String sentimiento = "Positivo";
        Double confianza = 0.92;

        //Crear respuesta para el usuario
        SentimentResponse response = new SentimentResponse();
        response.setPrevision(sentimiento);
        response.setProbabilidad(confianza);

        //Verificar estructura de la respuesta para el usuario
        assertEquals("Positivo", response.getPrevision(),
                "✅ La respuesta debe incluir el sentimiento que se detectó");

        assertEquals(0.92, response.getProbabilidad(),
                "✅ La respuesta debe incluir el porcentaje de confianza del sentimiento detectado");
    }

    @Test
    void ResultadoPositivo() {
        //Test simple - Verifica que se obtiene una respuesta positiva
        //Crear respuesta positiva para el usuario
        SentimentResponse resultado = new SentimentResponse("Positivo", 0.95);

        //Verificar estructura de la respuesta positiva para el usuario
        assertEquals("Positivo", resultado.getPrevision(),
                "📈 El usuario obtendrá un sentimiento positivo debido a que su texto es positivo.");

        assertEquals(0.95, resultado.getProbabilidad(),
                "🎯 El usuario obtendrá un porcentaje del 95% de confianza del sentimiento positivo calculado por el sistema.");
    }

    @Test
    void ResultadoNegativo() {
        //Test simple - Verifica que se obtiene una respuesta negativa
        //Crear respuesta negativa para el usuario

        SentimentResponse resultado = new SentimentResponse("Negativo", 0.88);

        assertEquals("Negativo", resultado.getPrevision(),
                "📉 El usuario obtendrá un sentimiento negativo debido a que su texto es negativo.");

        assertEquals(0.88, resultado.getProbabilidad(),
                "🎯 El usuario obtendrá un porcentaje del 88% de confianza del sentimiento negativo calculado por el sistema.");
    }

    @Test
    void ResultadoNeutro() {
        //Test simple - Verifica que se obtiene una respuesta neutra
        //Crear respuesta neutra para el usuario

        SentimentResponse resultado = new SentimentResponse("Neutro", 0.45);

        assertEquals("Neutro", resultado.getPrevision(),
                "📊 El usuario obtendrá un sentimiento neutro debido a que su texto es neutro (ni positivo ni negativo).");

        assertEquals(0.45, resultado.getProbabilidad(),
                "🎯 El usuario obtendrá un porcentaje del 45% de confianza del sentimiento neutro calculado por el sistema.");
    }
}