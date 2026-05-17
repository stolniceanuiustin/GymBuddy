package com.gymbuddy.backend.service;

import com.gymbuddy.backend.dto.RoutineTemplateDTO;
import java.util.List;

public interface RoutineTemplateService {
    RoutineTemplateDTO createTemplate(RoutineTemplateDTO dto);
    List<RoutineTemplateDTO> getTemplatesByUser(Long userId);
    void deleteTemplate(Long id);
    com.gymbuddy.backend.dto.GymDayDTO createGymDayFromTemplate(Long templateId);
}
