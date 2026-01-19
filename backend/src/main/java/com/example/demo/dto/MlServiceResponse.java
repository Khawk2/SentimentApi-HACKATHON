package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MlServiceResponse {
    @JsonProperty("prevision")
    private String prevision;

    @JsonProperty("probabilidad")
    private Double probabilidad;
}