package com.example.CycleSharingSystemBackend.service;

import com.example.CycleSharingSystemBackend.dto.FareSettingsDTO;
import com.example.CycleSharingSystemBackend.model.FareSettings;

public interface FareSettingsService {
    double getHourlyRate();
    double getDailyRate();
    double getWeeklyRate();
    double getMonthlyRate();

    FareSettings updateFareSettings(FareSettingsDTO fareSettingsDTO);

    FareSettings createFareSettings(FareSettingsDTO fareSettingsDTO);
}