package com.gymbuddy.backend.service.impl;

import com.gymbuddy.backend.dto.BodyFatPredictionRequest;
import com.gymbuddy.backend.dto.BodyFatPredictionResponse;
import com.gymbuddy.backend.model.BodyFatLog;
import com.gymbuddy.backend.model.User;
import com.gymbuddy.backend.repository.BodyFatLogRepository;
import com.gymbuddy.backend.repository.UserRepository;
import com.gymbuddy.backend.service.BodyFatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BodyFatServiceImpl implements BodyFatService {

    private final BodyFatLogRepository bodyFatLogRepository;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;

    @Value("${bodyfat.service.url}")
    private String bodyFatServiceUrl;

    @Override
    public BodyFatLog predictAndSave(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getGender() == null) {
            throw new RuntimeException("Gender is required for body fat prediction");
        }

        BodyFatPredictionRequest request = BodyFatPredictionRequest.builder()
                .weight(user.getWeight())
                .height(user.getHeight())
                .waist(user.getWaist())
                .hip(user.getHip())
                .thigh(user.getThigh())
                .arm(user.getArm())
                .age(user.getAge())
                .build();

        String url = bodyFatServiceUrl + "/predict/" + user.getGender().name().toLowerCase();
        
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<BodyFatPredictionRequest> entity = new HttpEntity<>(request, headers);

            BodyFatPredictionResponse response = restTemplate.postForObject(url, entity, BodyFatPredictionResponse.class);
            
            if (response == null || response.getPrediction() == null) {
                throw new RuntimeException("Prediction failed");
            }

            BodyFatLog log = BodyFatLog.builder()
                    .user(user)
                    .date(LocalDate.now())
                    .value(response.getPrediction())
                    .build();

            return bodyFatLogRepository.save(log);
        } catch (Exception e) {
            throw new RuntimeException("Error communicating with body fat service: " + e.getMessage(), e);
        }
    }

    @Override
    public List<BodyFatLog> getHistory(Long userId) {
        return bodyFatLogRepository.findByUserIdOrderByDateDesc(userId);
    }

    @Override
    public void deleteLog(Long id) {
        bodyFatLogRepository.deleteById(id);
    }
}
