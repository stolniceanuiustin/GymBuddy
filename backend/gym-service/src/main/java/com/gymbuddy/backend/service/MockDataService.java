package com.gymbuddy.backend.service;
import com.gymbuddy.backend.dto.GymDayDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MockDataService {
    void generateMockData();
    List<GymDayDTO> getGymDays();
}