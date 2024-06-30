package com.example.CycleSharingSystemBackend.controller;

import com.example.CycleSharingSystemBackend.dto.BikeMaintenanceDto;
import com.example.CycleSharingSystemBackend.dto.BikeMaintenanceRequestDto;
import com.example.CycleSharingSystemBackend.model.BikeMaintenance;
import com.example.CycleSharingSystemBackend.service.BikeMaintenanceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/v1/bikes/maintenance")
public class BikeMaintenanceController {

    private final BikeMaintenanceService bikeMaintenanceService;

    public BikeMaintenanceController(BikeMaintenanceService bikeMaintenanceService) {
        this.bikeMaintenanceService = bikeMaintenanceService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<BikeMaintenanceDto>> getAllBikeMaintenances() {
        List<BikeMaintenanceDto> bikeMaintenances = bikeMaintenanceService.getAllBikeMaintenances();
        return ResponseEntity.ok(bikeMaintenances);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createBikeMaintenance(@RequestBody BikeMaintenanceRequestDto bikeMaintenanceRequestDto) {
        this.bikeMaintenanceService.createBikeMaintenance(bikeMaintenanceRequestDto);
        return new ResponseEntity<>("Bike Maintenance created successfully", HttpStatus.CREATED);
    }

    @PutMapping("/update/{maintenanceId}")
    public ResponseEntity<String> updateBikeMaintenance(@RequestBody BikeMaintenance bikeMaintenance, @PathVariable Long maintenanceId) {
        boolean updated = this.bikeMaintenanceService.updateBikeMaintenance(bikeMaintenance, maintenanceId);
        return updated ? new ResponseEntity<>("Bike Maintenance updated successfully", HttpStatus.OK) : new ResponseEntity<>("Bike Maintenance not found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{maintenanceId}")
    public ResponseEntity<String> deleteBikeMaintenance(@PathVariable Long maintenanceId) {
        boolean deleted = this.bikeMaintenanceService.deleteBikeMaintenanceRecord(maintenanceId);
        return deleted ? new ResponseEntity<>("Bike Maintenance Record deleted successfully", HttpStatus.OK) : new ResponseEntity<>("Bike Maintenance Record not found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/bikes-in-maintenance-count")
    public Long getBikesInMaintenanceCount() {
        return bikeMaintenanceService.countBikeIdsInMaintenance();
    }
}
