package com.gymbuddy.backend.service.impl;

import com.gymbuddy.backend.model.ExerciseSet;
import com.gymbuddy.backend.repository.ExerciseSetRepository;
import com.gymbuddy.backend.service.AchievementService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class AchievementServiceImpl implements AchievementService {

    private final ExerciseSetRepository exerciseSetRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public AchievementServiceImpl(ExerciseSetRepository exerciseSetRepository, 
                                  SimpMessagingTemplate messagingTemplate) {
        this.exerciseSetRepository = exerciseSetRepository;
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void checkPersonalBest(Long userId, Long exerciseTypeId, String exerciseName, ExerciseSet set) {
        if (set.getWeight() == null || set.getWeight() <= 0) {
            return;
        }

        Double previousMax = exerciseSetRepository.findMaxWeightByUserIdAndExerciseTypeId(userId, exerciseTypeId, set.getId());

        if (previousMax == null || set.getWeight() > previousMax) {
            String message = String.format("CONGRATULATIONS! New Personal Best! You just hit %.2f kg on %s!",
                                            set.getWeight(), exerciseName);
            messagingTemplate.convertAndSend("/topic/socket/gym", message);
        }
    }
}
