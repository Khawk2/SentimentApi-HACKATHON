package com.example.demo.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class GeminiConsultationTest {

    @Test
    void emptyText_returnsEmpty() {
        GeminiConsultation service =
                new GeminiConsultation(Mockito.mock(Client.class));

        assertEquals("", service.traducirAlEspanol(""));
    }
}
