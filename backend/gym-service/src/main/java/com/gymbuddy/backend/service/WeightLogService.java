package com.gymbuddy.backend.service;

import com.gymbuddy.backend.model.WeightLog;
import java.util.List;

public interface WeightLogService {
    List<WeightLog> getWeightHistory(Long userId);
    WeightLog addWeightLog(Long userId, Float weight);
    void deleteWeightLog(Long id);
}
