package com.example.CycleSharingSystemBackend.service.impl;

import com.example.CycleSharingSystemBackend.dto.BikeMaintenanceDto;
import com.example.CycleSharingSystemBackend.dto.BikeMaintenanceRequestDto;
import com.example.CycleSharingSystemBackend.model.BikeMaintenance;
import com.example.CycleSharingSystemBackend.model.Bikes;
import com.example.CycleSharingSystemBackend.repository.BikeMaintenanceRepository;
import com.example.CycleSharingSystemBackend.repository.Bikerepository;
import com.example.CycleSharingSystemBackend.service.BikeMaintenanceService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BikeMaintenanceServiceImpl implements BikeMaintenanceService {
    private final BikeMaintenanceRepository bikeMaintenanceRepository;

    @Autowired
    private Bikerepository bikerepository;

    public BikeMaintenanceServiceImpl(BikeMaintenanceRepository bikeMaintenanceRepository) {
        this.bikeMaintenanceRepository = bikeMaintenanceRepository;
    }

    @Override
    public void createBikeMaintenance(BikeMaintenanceRequestDto bikeMaintenanceRequestDto) {
        // Fetch the Bikes entity using the bikeId from the DTO
        Optional<Bikes> bikeOptional = this.bikerepository.findById(bikeMaintenanceRequestDto.getBikeId());

        // Check if the bike is present
        if (!bikeOptional.isPresent()) {
            throw new EntityNotFoundException("Bike not found with id: " + bikeMaintenanceRequestDto.getBikeId());
        }

        Bikes bike = bikeOptional.get();

        // Create and set up BikeMaintenance entity
        BikeMaintenance bikeMaintenance = new BikeMaintenance();
        bikeMaintenance.setBikes(bike);
        bikeMaintenance.setReportedDate(bikeMaintenanceRequestDto.getReportedDate());
        bikeMaintenance.setType(bikeMaintenanceRequestDto.getType());
        bikeMaintenance.setComments(bikeMaintenanceRequestDto.getComments());
        bikeMaintenance.setStatus(true); // Set status to true by default

        // Save the BikeMaintenance entity
        this.bikeMaintenanceRepository.save(bikeMaintenance);
    }


    @Override
    public boolean updateBikeMaintenance(BikeMaintenance maintenance, Long maintenanceId) {
        Optional<BikeMaintenance> existingMaintenanceOptional = this.bikeMaintenanceRepository.findById(maintenanceId);
        if (existingMaintenanceOptional.isPresent()) {
            BikeMaintenance existingMaintenance = existingMaintenanceOptional.get();

            existingMaintenance.setReportedDate(maintenance.getReportedDate());
            existingMaintenance.setType(maintenance.getType());
            existingMaintenance.setStatus(maintenance.isStatus());
            existingMaintenance.setComments(maintenance.getComments());
            this.bikeMaintenanceRepository.save(existingMaintenance);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteBikeMaintenanceRecord(Long maintenanceId) {
        Optional<BikeMaintenance> existingMaintenanceOptional = this.bikeMaintenanceRepository.findById(maintenanceId);
        if (existingMaintenanceOptional.isPresent()) {
            this.bikeMaintenanceRepository.deleteById(maintenanceId);
            return true;
        } else {
            return false;
        }
    }

    public List<BikeMaintenanceDto> getAllBikeMaintenances() {
        List<BikeMaintenance> bikeMaintenances = bikeMaintenanceRepository.findAll();
        return bikeMaintenances.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private BikeMaintenanceDto convertToDto(BikeMaintenance bikeMaintenance) {
        BikeMaintenanceDto dto = new BikeMaintenanceDto();
        dto.setMaintenanceId(bikeMaintenance.getMaintenanceId());
        dto.setBikeId(bikeMaintenance.getBikes().getBikeId());
        dto.setType(bikeMaintenance.getType());
        dto.setStatus(bikeMaintenance.isStatus());
        dto.setComments(bikeMaintenance.getComments());
        dto.setReportedDate(bikeMaintenance.getReportedDate());
        return dto;
    }

    @Override
    public Long countBikeIdsInMaintenance() {
        return this.bikeMaintenanceRepository.countDistinctBikeIdsInMaintenance();
    }
}
