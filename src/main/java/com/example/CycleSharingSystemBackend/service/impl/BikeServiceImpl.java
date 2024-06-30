package com.example.CycleSharingSystemBackend.service.impl;


import com.example.CycleSharingSystemBackend.dto.BikeDto;
import com.example.CycleSharingSystemBackend.dto.BikeRequestDTO;
import com.example.CycleSharingSystemBackend.model.Bikes;
import com.example.CycleSharingSystemBackend.model.Station;
import com.example.CycleSharingSystemBackend.repository.Bikerepository;
import com.example.CycleSharingSystemBackend.repository.Stationrepository;
import com.example.CycleSharingSystemBackend.service.BikeService;
import com.example.CycleSharingSystemBackend.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BikeServiceImpl implements BikeService {

    @Autowired
    private Bikerepository bikeRepository;

    @Autowired
    private StationService stationService;
    private final Stationrepository stationrepository;

    public BikeServiceImpl(Stationrepository stationrepository) {
        this.stationrepository = stationrepository;
    }


//    @Override
//    public Bikes newBike(BikeRequestDTO newBike) {
//        String stationId = newBike.getStationId();
//        Optional<Station> stationOptional = stationService.getStationById(stationId);
//        if (stationOptional.isPresent()) {
//            Station station = stationOptional.get();
//            Bikes bike = newBike.getBike();
//            bike.setStation(station);
//
//            List<Bikes> bikesInStation = station.getBikes();
//            bikesInStation.add(bike);
//            station.setBikes(bikesInStation);
//            station.setAvailableBike(bikesInStation.size());
//            stationService.saveStation(station);
//            return bikeRepository.save(bike);
//        } else {
//            return null;
//        }
//    }

    @Override
    public BikeDto newBike(BikeRequestDTO newBikeDto) {
        String stationId = newBikeDto.getInitStationId();
        Optional<Station> stationOptional = stationrepository.findByStationId(stationId);

        if (stationOptional.isPresent()) {
            Station station = stationOptional.get();

            Bikes bike = new Bikes();
            bike.setBrand(newBikeDto.getBrand());
            bike.setBikeModel(newBikeDto.getBikeModel());
            bike.setBikeCode(newBikeDto.getBikeCode());
            bike.setColor(newBikeDto.getColor());
            bike.setOnRide(false);
            bike.setInitStationId(stationId);
            bike.setStation(station);



            // Update the station's bike list
            List<Bikes> bikesInStation = station.getBikes();
            bikesInStation.add(bike);
            station.setBikes(bikesInStation);
            bikeRepository.save(bike);
            stationService.saveStation(station);

            System.out.println("Added bike ID: " + bike.getBikeId() + " to station ID: " + stationId);
            return convertToDto(bike);
        } else {
            throw new RuntimeException("Station not found with id: " + stationId);
        }
    }

    private BikeDto convertToDto(Bikes bike) {
        BikeDto dto = new BikeDto();
        dto.setBikeId(bike.getBikeId());
        dto.setBrand(bike.getBrand());
        dto.setBikeModel(bike.getBikeModel());
        dto.setBikeCode(bike.getBikeCode());
        dto.setLastMaintenanceDate(bike.getLastMaintenanceDate());
        dto.setColor(bike.getColor());
        dto.setOnRide(bike.isOnRide());
        dto.setStationId(bike.getStation() != null ? bike.getStation().getStationId() : null);
        return dto;
    }

    @Override
    public List<BikeDto> getAllBikes() {
        List<Bikes> bikes = bikeRepository.findAll();
        return bikes.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public Optional<BikeDto> getBikeById(Long bikeId) {
        return bikeRepository.findById(bikeId).map(this::convertToDto);
    }

    @Override
    public BikeDto updateBike(BikeDto newBike, Long bikeId) {
        Bikes updatedBike = bikeRepository.findById(bikeId)
                .map(existingBike -> {
                    existingBike.setBikeCode(newBike.getBikeCode());
                    existingBike.setBikeModel(newBike.getBikeModel());
                    existingBike.setBrand(newBike.getBrand());
                    existingBike.setColor(newBike.getColor());
//                    existingBike.setLastMaintenanceDate(newBike.getLastMaintenanceDate());
                    return bikeRepository.save(existingBike);
                })
                .orElseThrow(() -> new RuntimeException("Bike not found with id: " + bikeId));

        return convertToDto(updatedBike);
    }

    @Override
    public String deleteBike(Long bikeId) {
        if (!bikeRepository.existsById(bikeId)) {
            throw new RuntimeException("Bike not found with id: " + bikeId);
        }
        bikeRepository.deleteById(bikeId);
        return "Bike with id " + bikeId + " has been deleted successfully.";
    }

    @Override
    public Long getLatestBikeId() {
        return bikeRepository.findLatestBikeId();
    }

    @Override
    public int getTotalBikes() {
        List<Bikes> allBikes = bikeRepository.findAll();
        return allBikes.size();
    }

    @Override
    public BikeDto getLatestBikeDetails() {
        Bikes latestBike = bikeRepository.findLatestBikeDetails();
        return convertToDto(latestBike);
    }
}