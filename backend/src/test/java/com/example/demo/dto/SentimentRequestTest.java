package com.example.demo.dto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SentimentRequestTest {
    @Test
    void ProcesarPeticion() {
        //Test simple - Verifica que se pueda procesar la petición
        //Enviar JSON del cliente
        String jsonDelCliente = """
        {
          "text": "Excelente producto y servicio."
        }
        """;

        //Convertir JSON a SentimentRequest (de manera automática con Spring)
        SentimentRequest request = new SentimentRequest();
        request.setText("Excelente producto y servicio.");

        //Utilizar el texto
        String textoAAnalizar = request.getText();

        //Verificar el texto a analizar
        assertEquals("Excelente producto y servicio.", textoAAnalizar);
    }

    @Test
    void ValidarTexto() {
        //Test simple - Valida o invalida textos
        //Texto null
        SentimentRequest invalidtext1 = new SentimentRequest();
        invalidtext1.setText(null);

        //Texto vacío
        SentimentRequest invalidtext2 = new SentimentRequest();
        invalidtext2.setText("");

        //Texto válido
        SentimentRequest validtext = new SentimentRequest();
        validtext.setText("¡Gran producto!");
    }
}