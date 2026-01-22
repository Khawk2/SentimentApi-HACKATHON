package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class InfluentialWord {
    @JsonProperty("word")
    private String word;

    @JsonProperty("importance")
    private Double importance;

    @JsonProperty("sentiment")
    private String sentiment;
}
