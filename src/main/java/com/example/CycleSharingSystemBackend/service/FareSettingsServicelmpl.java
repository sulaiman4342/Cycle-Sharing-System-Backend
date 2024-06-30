package com.example.CycleSharingSystemBackend.service;


import com.example.CycleSharingSystemBackend.dto.FareSettingsDTO;
import com.example.CycleSharingSystemBackend.model.FareSettings;
import com.example.CycleSharingSystemBackend.repository.FareSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.*;
@Service
public class FareSettingsServicelmpl implements FareSettingsService {
    @Autowired
    private FareSettingsRepository fareSettingsRepository;
    @Override
    public double getHourlyRate() {
        FareSettings fareSettings = fareSettingsRepository.findTopByOrderByUpdatedAtDesc().orElse(null);
        return (fareSettings != null) ? fareSettings.getHourlyRate() : -1; // Or handle null case as needed
    }
    @Override
    public double getDailyRate() {
        FareSettings fareSettings = fareSettingsRepository.findTopByOrderByUpdatedAtDesc().orElse(null);
        return (fareSettings != null) ? fareSettings.getDailyRate() : -1; // Or handle null case as needed
    }
    @Override
    public double getWeeklyRate() {
        FareSettings fareSettings = fareSettingsRepository.findTopByOrderByUpdatedAtDesc().orElse(null);
        return (fareSettings != null) ? fareSettings.getWeeklyRate() : -1; // Or handle null case as needed
    }
    @Override
    public double getMonthlyRate() {
        FareSettings fareSettings = fareSettingsRepository.findTopByOrderByUpdatedAtDesc().orElse(null);
        return (fareSettings != null) ? fareSettings.getMonthlyRate() : -1; // Or handle null case as needed
    }
    @Override
    public FareSettings updateFareSettings(FareSettingsDTO fareSettingsDTO) {
        FareSettings fareSettings = new FareSettings();
        fareSettingsRepository.findTopByOrderByUpdatedAtDesc().ifPresentOrElse(
                existingSettings -> {
                    fareSettings.setId(existingSettings.getId());
                    fareSettings.setCreatedAt(existingSettings.getCreatedAt());                },
                () -> fareSettings.setCreatedAt(LocalDateTime.now())        );
        fareSettings.setHourlyRate(fareSettingsDTO.getHourlyRate());
        fareSettings.setDailyRate(fareSettingsDTO.getDailyRate());
        fareSettings.setWeeklyRate(fareSettingsDTO.getWeeklyRate());
        fareSettings.setMonthlyRate(fareSettingsDTO.getMonthlyRate());
        fareSettings.setUpdatedAt(LocalDateTime.now());
        return fareSettingsRepository.save(fareSettings);    }
    @Override
    public FareSettings createFareSettings(FareSettingsDTO fareSettingsDTO) {
        FareSettings fareSettings = new FareSettings();
        // Set the fare settings based on the DTO
        fareSettings.setHourlyRate(fareSettingsDTO.getHourlyRate());
        fareSettings.setDailyRate(fareSettingsDTO.getDailyRate());
        fareSettings.setWeeklyRate(fareSettingsDTO.getWeeklyRate());
        fareSettings.setMonthlyRate(fareSettingsDTO.getMonthlyRate());
        fareSettings.setCreatedAt(LocalDateTime.now());
        fareSettings.setUpdatedAt(LocalDateTime.now());
        return fareSettingsRepository.save(fareSettings);    }
}