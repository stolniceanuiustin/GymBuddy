package com.gymbuddy.backend.service;

import com.gymbuddy.backend.model.BodyFatLog;
import java.util.List;

public interface BodyFatService {
    BodyFatLog predictAndSave(Long userId);
    List<BodyFatLog> getHistory(Long userId);
    void deleteLog(Long id);
}
