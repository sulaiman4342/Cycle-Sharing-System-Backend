package com.example.CycleSharingSystemBackend.controller;

import com.example.CycleSharingSystemBackend.dto.FareSettingsDTO;
import com.example.CycleSharingSystemBackend.model.Bikes;
import com.example.CycleSharingSystemBackend.model.FareSettings;
import com.example.CycleSharingSystemBackend.repository.FareSettingsRepository;
import com.example.CycleSharingSystemBackend.service.FareSettingsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/v1/fare")
public class FareSettingsController {

    @Autowired
    private FareSettingsService fareSettingsService;
    @GetMapping("/hourly-rate")
    public double getHourlyRate() {
        return fareSettingsService.getHourlyRate();    }
    @GetMapping("/daily-rate")
    public double getDailyRate() {  return fareSettingsService.getDailyRate();
    }
    @GetMapping("/weekly-rate")    public double getWeeklyRate() {
        return fareSettingsService.getWeeklyRate();    }
    @GetMapping("/monthly-rate")
    public double getMonthlyRate() {  return fareSettingsService.getMonthlyRate();
    }


    @PutMapping("/settings")    public FareSettingsDTO updateFareSettings(@RequestBody FareSettingsDTO fareSettingsDTO) {
        FareSettings updatedFareSettings = fareSettingsService.updateFareSettings(fareSettingsDTO);  return mapToDTO(updatedFareSettings);
    }
    @PostMapping("/settings")
    public FareSettingsDTO createFareSettings(@RequestBody FareSettingsDTO fareSettingsDTO) { FareSettings newFareSettings = fareSettingsService.createFareSettings(fareSettingsDTO);
        return mapToDTO(newFareSettings);}
    private FareSettingsDTO mapToDTO(FareSettings fareSettings) {
        FareSettingsDTO fareSettingsDTO = new FareSettingsDTO();
        fareSettingsDTO.setHourlyRate(fareSettings.getHourlyRate());
        fareSettingsDTO.setDailyRate(fareSettings.getDailyRate());
        fareSettingsDTO.setWeeklyRate(fareSettings.getWeeklyRate());
        fareSettingsDTO.setMonthlyRate(fareSettings.getMonthlyRate());
        return fareSettingsDTO;
    }
}