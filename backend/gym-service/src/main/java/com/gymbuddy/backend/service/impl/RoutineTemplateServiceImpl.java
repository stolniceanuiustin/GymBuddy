package com.gymbuddy.backend.service.impl;

import com.gymbuddy.backend.dto.ExerciseDTO;
import com.gymbuddy.backend.dto.ExerciseSetDTO;
import com.gymbuddy.backend.dto.GymDayDTO;
import com.gymbuddy.backend.dto.RoutineTemplateDTO;
import com.gymbuddy.backend.model.ExerciseType;
import com.gymbuddy.backend.model.RoutineTemplate;
import com.gymbuddy.backend.repository.ExerciseTypeRepository;
import com.gymbuddy.backend.repository.RoutineTemplateRepository;
import com.gymbuddy.backend.service.GymDayService;
import com.gymbuddy.backend.service.RoutineTemplateService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoutineTemplateServiceImpl implements RoutineTemplateService {

    private final RoutineTemplateRepository routineTemplateRepository;
    private final ExerciseTypeRepository exerciseTypeRepository;
    private final GymDayService gymDayService;

    public RoutineTemplateServiceImpl(RoutineTemplateRepository routineTemplateRepository,
                                     ExerciseTypeRepository exerciseTypeRepository,
                                     GymDayService gymDayService) {
        this.routineTemplateRepository = routineTemplateRepository;
        this.exerciseTypeRepository = exerciseTypeRepository;
        this.gymDayService = gymDayService;
    }

    @Override
    public RoutineTemplateDTO createTemplate(RoutineTemplateDTO dto) {
        List<ExerciseType> exerciseTypes = exerciseTypeRepository.findAllById(dto.getExerciseTypeIds());
        
        RoutineTemplate template = RoutineTemplate.builder()
                .name(dto.getName())
                .userId(dto.getUserId())
                .exerciseTypes(exerciseTypes)
                .build();
        
        RoutineTemplate saved = routineTemplateRepository.save(template);
        return mapToDTO(saved);
    }

    @Override
    public List<RoutineTemplateDTO> getTemplatesByUser(Long userId) {
        return routineTemplateRepository.findByUserId(userId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteTemplate(Long id) {
        routineTemplateRepository.deleteById(id);
    }

    @Override
    public GymDayDTO createGymDayFromTemplate(Long templateId) {
        RoutineTemplate template = routineTemplateRepository.findById(templateId)
                .orElseThrow(() -> new RuntimeException("Template not found"));
        
        List<ExerciseDTO> exercises = template.getExerciseTypes().stream()
                .map(type -> ExerciseDTO.builder()
                        .exerciseTypeId(type.getId())
                        .exerciseTypeName(type.getName())
                        .sets(Collections.singletonList(ExerciseSetDTO.builder()
                                .setNumber(1)
                                .reps(10)
                                .weight(0.0)
                                .build()))
                        .build())
                .collect(Collectors.toList());
        
        GymDayDTO gymDayDTO = GymDayDTO.builder()
                .name(template.getName() + " Session")
                .date(LocalDate.now())
                .userId(template.getUserId())
                .exercises(exercises)
                .sleepQuality(5)
                .energyLevel(5)
                .build();
        
        return gymDayService.addGymDay(gymDayDTO);
    }

    private RoutineTemplateDTO mapToDTO(RoutineTemplate template) {
        return RoutineTemplateDTO.builder()
                .id(template.getId())
                .name(template.getName())
                .userId(template.getUserId())
                .exerciseTypeIds(template.getExerciseTypes().stream().map(ExerciseType::getId).collect(Collectors.toList()))
                .exerciseTypeNames(template.getExerciseTypes().stream().map(ExerciseType::getName).collect(Collectors.toList()))
                .build();
    }
}
