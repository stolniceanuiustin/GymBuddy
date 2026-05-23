package com.gymbuddy.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BodyFatPredictionRequest {
    @JsonProperty("Weight")
    private Float weight;
    @JsonProperty("Height")
    private Float height;
    @JsonProperty("Waist")
    private Float waist;
    @JsonProperty("Hip")
    private Float hip;
    @JsonProperty("Thigh")
    private Float thigh;
    @JsonProperty("Arm")
    private Float arm;
    @JsonProperty("Age")
    private Integer age;
}
