package com.example.CycleSharingSystemBackend.service.impl;

import com.example.CycleSharingSystemBackend.dto.*;
import com.example.CycleSharingSystemBackend.model.*;
import com.example.CycleSharingSystemBackend.repository.*;
import com.example.CycleSharingSystemBackend.service.BikeService;
import com.example.CycleSharingSystemBackend.service.LocationService;
import com.example.CycleSharingSystemBackend.service.RideService;
import com.example.CycleSharingSystemBackend.service.StationService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RideServiceImpl implements RideService {
    @Autowired
    private RideRepository rideRepository;
    @Autowired
    private RidePathRepository ridePathRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Bikerepository bikerepository;

    @Autowired
    private Stationrepository stationrepository;
    @Autowired
    private StationService stationService;

    @Autowired
    private BikeService bikeService;

    @Autowired
    private LocationService locationService;


    @Override
    public Ride saveRideHistory(Ride rideHistory) {
        return rideRepository.save(rideHistory);
    }

    @Override
    public RideDto updateRidePath(UpdateRidePathDto updateRidePathDto) {
        Optional<Ride> optionalRide = rideRepository.findById(updateRidePathDto.getRideId());
        if (optionalRide.isPresent()) {
            Ride ride = optionalRide.get();
            if (ride.isInRide()) {
                RidePath ridePath = new RidePath();
                ridePath.setLatitude(updateRidePathDto.getLatitude());
                ridePath.setLongitude(updateRidePathDto.getLongitude());
                ridePath.setRide(ride);
                ridePath.setTimestamp(LocalDateTime.now());

                ride.getRidePaths().add(ridePath);
                rideRepository.save(ride);

                return convertToDto(ride);
            } else {
                throw new IllegalStateException("Ride has already ended.");
            }
        } else {
            throw new IllegalArgumentException("Ride not found with ID: " + updateRidePathDto.getRideId());
        }
    }


    public List<RidePath> getRidePath(Long rideId) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new EntityNotFoundException("Ride not found"));

        return ridePathRepository.findByRideOrderByTimestamp(ride);
    }

    @Override
    public RideDto startRide(StartRideRequestDto startRideRequestDto) {
        // Fetch the start station
        Station startStation = stationrepository.findByStationId(startRideRequestDto.getStartStationId())
                .orElseThrow(() -> new RuntimeException("Station not found with id: " + startRideRequestDto.getStartStationId()));

        // Fetch the bike and check if it is at the start station
        Bikes bike = bikeService.getBikeById(startRideRequestDto.getBikeId())
                .map(this::convertToEntity)
                .orElseThrow(() -> new IllegalArgumentException("Bike not found"));

        if (!startStation.getBikes().contains(bike)) {
            throw new IllegalArgumentException("Bike not found at the start station");
        }

        // Remove the bike from the start station
        stationService.removeBikeFromStation(bike, startStation);

        // Fetch the user and set them in ride
        User user = userRepository.findById(startRideRequestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setInRide(true);
        userRepository.save(user);

        // Set bike on ride
        bike.setOnRide(true);
        bikerepository.save(bike);

        // Create and save the new ride
        Ride ride = new Ride();
        ride.setUser(user);
        ride.setBike(bike);
        ride.setStartStation(startStation);
        ride.setInRide(true);
        ride.setStartTime(LocalDateTime.now());

        Ride savedRide = rideRepository.save(ride);

        return convertToDto(savedRide);
    }

    // Utility method to convert BikeDto to Bikes entity
    private Bikes convertToEntity(BikeDto bikeDto) {
        Bikes bike = new Bikes();
        bike.setBikeId(bikeDto.getBikeId());
        bike.setBrand(bikeDto.getBrand());
        bike.setBikeModel(bikeDto.getBikeModel());
        bike.setBikeCode(bikeDto.getBikeCode());
        bike.setLastMaintenanceDate(bikeDto.getLastMaintenanceDate());
        bike.setColor(bikeDto.getColor());
        bike.setOnRide(bikeDto.isOnRide());
        return bike;
    }

    private RideDto convertToDto(Ride ride) {
        RideDto rideDto = new RideDto();
        rideDto.setRideId(ride.getRideId());
        rideDto.setUserId(ride.getUser().getUserId());
        rideDto.setStartStationId(ride.getStartStation().getStationId());
        rideDto.setEndStationId(ride.getEndStation() != null ? ride.getEndStation().getStationId() : null);
        rideDto.setEnRide(ride.isInRide());
        rideDto.setStartTime(ride.getStartTime());
        rideDto.setEndTime(ride.getEndTime());
        rideDto.setBikeId(ride.getBike().getBikeId()); // Corrected line
        rideDto.setRidePaths(ride.getRidePaths().stream().map(this::convertRidePathToDto).collect(Collectors.toList()));
        return rideDto;
    }

    private RidePathDto convertRidePathToDto(RidePath ridePath) {
        RidePathDto ridePathDto = new RidePathDto();
        ridePathDto.setId(ridePath.getId());
        ridePathDto.setRideId(ridePath.getRide().getRideId());
        ridePathDto.setLatitude(ridePath.getLatitude());
        ridePathDto.setLongitude(ridePath.getLongitude());
        ridePathDto.setTimestamp(ridePath.getTimestamp());
        return ridePathDto;
    }


    @Override
    public RideDto endRide(EndRideRequestDto endRideRequestDto) {
        // Fetch the ride by ID
        Ride ride = rideRepository.findById(endRideRequestDto.getRideId())
                .orElseThrow(() -> new IllegalArgumentException("Ride not found"));

        // Check if the ride is currently marked as inRide
        if (!ride.isInRide()) {
            throw new IllegalStateException("Ride with ID " + ride.getRideId() + " is not in progress.");
        }

        // Fetch the end station
        StationDto endStationDto = stationService.getStationById(endRideRequestDto.getEndStationId())
                .orElseThrow(() -> new IllegalArgumentException("End station not found"));
        Station endStation = convertToEntity(endStationDto);

        // Initialize bikes list in the end station if null
        if (endStation.getBikes() == null) {
            endStation.setBikes(new ArrayList<>());
        }

        // Check if the end station has available parking slots
        if (endStation.getAvailableParkingSlots() <= 0) {
            throw new IllegalStateException("End station " + endStation.getStationId() + " has no available parking slots.");
        }

        // Fetch the bike
        Bikes bike = bikeService.getBikeById(ride.getBike().getBikeId())
                .map(this::convertToEntity)
                .orElseThrow(() -> new IllegalArgumentException("Bike not found"));

        // Add bike to the end station
        stationService.addBikeToStation(bike, endStation);

        // Update user status
        User user = ride.getUser();
        user.setInRide(false);
        userRepository.save(user);

        // Update bike status
        bike.setOnRide(false);
        bikerepository.save(bike);

        // Update and save ride
        ride.setInRide(false);
        ride.setEndStation(endStation);
        ride.setEndTime(LocalDateTime.now());

        return convertToDto(rideRepository.save(ride));
    }
    private Station convertToEntity(StationDto stationDto) {
        Station station = new Station();
        station.setStationId(stationDto.getStationId());
        station.setName(stationDto.getName());
        station.setCapacity(stationDto.getCapacity());
        station.setLocation(convertToEntity(stationDto.getLocation()));
        // Add any additional field mappings here
        return station;
    }
    private Location convertToEntity(LocationDto locationDto) {
        Location location = new Location();
        location.setLocationId(LocationServicelmpl.generateLocationId()); // Generate location ID
        location.setLatitude(locationDto.getLatitude());
        location.setLongitude(locationDto.getLongitude());
        return location;
    }


    @Override
    public List<RideDto> getRideHistoryForUser(Long userId) {
        List<Ride> rides = rideRepository.findByUserUserId(userId);
        return rides.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RidePathSimpleDto> getActiveRidePaths() {
        List<Ride> activeRides = rideRepository.findByInRide(true);
        List<RidePathSimpleDto> activeRidePaths = new ArrayList<>();

        for (Ride ride : activeRides) {
            RidePath latestRidePath = ride.getRidePaths().stream()
                    .max((p1, p2) -> p1.getTimestamp().compareTo(p2.getTimestamp()))
                    .orElseThrow(() -> new IllegalArgumentException("No paths found for ride ID: " + ride.getRideId()));

            RidePathSimpleDto dto = new RidePathSimpleDto();
            dto.setRideId(ride.getRideId());
            dto.setLatitude(latestRidePath.getLatitude());
            dto.setLongitude(latestRidePath.getLongitude());
            activeRidePaths.add(dto);
        }

        return activeRidePaths;
    }

    @Override
    public RidePathSimpleDto getCurrentLocation(Long rideId) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new IllegalArgumentException("Ride not found"));

        RidePath latestRidePath = ride.getRidePaths().stream()
                .max((p1, p2) -> p1.getTimestamp().compareTo(p2.getTimestamp()))
                .orElseThrow(() -> new IllegalArgumentException("No paths found for the ride"));

        RidePathSimpleDto dto = new RidePathSimpleDto();
        dto.setRideId(ride.getRideId());
        dto.setLatitude(latestRidePath.getLatitude());
        dto.setLongitude(latestRidePath.getLongitude());

        return dto;
    }
    @Override
    public RideDto getRideById(Long rideId) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new EntityNotFoundException("Ride not found with ID: " + rideId));
        return convertToDto(ride);
    }
    @Override
    public List<RideDto> getOnRideRides() {
        List<Ride> onRideRides = rideRepository.findByInRide(true);
        return onRideRides.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public long getTotalRides() {
        return rideRepository.countTotalRides();
    }
}
