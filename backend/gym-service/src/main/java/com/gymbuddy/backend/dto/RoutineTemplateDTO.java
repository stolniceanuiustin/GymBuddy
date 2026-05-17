package com.gymbuddy.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoutineTemplateDTO {
    private Long id;
    private String name;
    private Long userId;
    private List<Long> exerciseTypeIds;
    private List<String> exerciseTypeNames;
}
