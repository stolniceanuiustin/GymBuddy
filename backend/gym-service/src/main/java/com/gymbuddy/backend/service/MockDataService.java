package com.gymbuddy.backend.service;
import com.gymbuddy.backend.model.GymDay;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MockDataService {
    void generateMockData();
    List<GymDay> getGymDays();
}