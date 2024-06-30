package com.example.CycleSharingSystemBackend.service;

import com.example.CycleSharingSystemBackend.dto.BikeMaintenanceDto;
import com.example.CycleSharingSystemBackend.dto.BikeMaintenanceRequestDto;
import com.example.CycleSharingSystemBackend.model.BikeMaintenance;

import java.util.List;

public interface BikeMaintenanceService {
    void createBikeMaintenance(BikeMaintenanceRequestDto bikeMaintenanceRequestDto);

    boolean updateBikeMaintenance(BikeMaintenance maintenance, Long maintenanceId);

    boolean deleteBikeMaintenanceRecord(Long maintenanceId);

    public List<BikeMaintenanceDto> getAllBikeMaintenances();

    Long countBikeIdsInMaintenance();
}
