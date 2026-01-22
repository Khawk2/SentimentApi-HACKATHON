package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class MlServiceResponse {
    @JsonProperty("prevision")
    private String prevision;

    @JsonProperty("probabilidad")
    private Double probabilidad;

    @JsonProperty("palabras_influyentes")
    private List<InfluentialWord> palabrasInfluyentes;

    @JsonProperty("explicacion")
    private String explicacion;
}