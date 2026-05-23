package com.gymbuddy.backend.dto;

import lombok.Data;

@Data
public class BodyFatPredictionResponse {
    private String gender;
    private Float prediction;
}
