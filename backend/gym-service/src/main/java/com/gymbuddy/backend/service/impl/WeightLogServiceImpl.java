package com.gymbuddy.backend.service.impl;

import com.gymbuddy.backend.exception.ResourceNotFoundException;
import com.gymbuddy.backend.exception.ValidationException;
import com.gymbuddy.backend.model.User;
import com.gymbuddy.backend.model.WeightLog;
import com.gymbuddy.backend.repository.UserRepository;
import com.gymbuddy.backend.repository.WeightLogRepository;
import com.gymbuddy.backend.service.WeightLogService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class WeightLogServiceImpl implements WeightLogService {

    private final WeightLogRepository weightLogRepository;
    private final UserRepository userRepository;

    public WeightLogServiceImpl(WeightLogRepository weightLogRepository, UserRepository userRepository) {
        this.weightLogRepository = weightLogRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<WeightLog> getWeightHistory(Long userId) {
        return weightLogRepository.findByUserIdOrderByDateAsc(userId);
    }

    @Override
    public WeightLog addWeightLog(Long userId, Float weight) {
        if (weight != null && (weight <= 0 || weight > 500)) {
            throw new ValidationException("Weight must be between 0 and 500 kg");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        
        WeightLog log = WeightLog.builder()
                .date(LocalDate.now())
                .weight(weight)
                .user(user)
                .build();
        
        return weightLogRepository.save(log);
    }

    @Override
    public void deleteWeightLog(Long id) {
        if (!weightLogRepository.existsById(id)) {
            throw new ResourceNotFoundException("WeightLog", "id", id);
        }
        weightLogRepository.deleteById(id);
    }
}
