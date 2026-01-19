package com.example.demo.dto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MlServiceResponseTest {
    @Test
    void AlmacenarPrevision() {
        //Test simple - Verifica que se pueda guardar y leer la previsión (sentimiento)
        //Crear un objeto MlServiceResponse
        MlServiceResponse response = new MlServiceResponse();

        //Guardar el valor de previsión
        String sentimientoEsperado = "Positivo";
        response.setPrevision(sentimientoEsperado);

        //Leer el valor de previsión
        String sentimientoObtenido = response.getPrevision();

        //Verificar que los valores de previsión sean iguales
        assertEquals(sentimientoEsperado, sentimientoObtenido,
                "La previsión guardada debe ser igual a la previsión leída");
    }
    @Test
    void AlmacenarProbabilidad() {
        //Test simple - Verifica que se pueda guardar y leer la probabilidad
        //Crear un objeto MlServiceResponse
        MlServiceResponse response = new MlServiceResponse();

        //Guardar el valor de probabilidad
        Double probabilidadEsperada = 0.95;
        response.setProbabilidad(probabilidadEsperada);

        //Leer el valor de probabilidad
        Double probabilidadObtenida = response.getProbabilidad();

        //Verificar que los valores de probabilidad sean iguales
        assertEquals(probabilidadEsperada, probabilidadObtenida,
                "La probabilidad guardada debe ser igual a la probabilidad leída");
    }

    @Test
    void DistintasRespuestas() {
        //Test simple - Verifica que puede manejar las distintas respuestas del servicio ML
        //Respuesta positiva
        MlServiceResponse response1 = new MlServiceResponse();
        response1.setPrevision("Positiva");
        response1.setProbabilidad(0.98);
        assertEquals("Positiva", response1.getPrevision());
        assertEquals(0.98, response1.getProbabilidad());

        //Respuesta negativa
        MlServiceResponse response2 = new MlServiceResponse();
        response2.setPrevision("Negativa");
        response2.setProbabilidad(0.75);
        assertEquals("Negativa", response2.getPrevision());
        assertEquals(0.75, response2.getProbabilidad());

        //Respuesta neutra
        MlServiceResponse response3 = new MlServiceResponse();
        response3.setPrevision("Neutra");
        response3.setProbabilidad(0.60);
        assertEquals("Neutra", response3.getPrevision());
        assertEquals(0.60, response3.getProbabilidad());
    }
}