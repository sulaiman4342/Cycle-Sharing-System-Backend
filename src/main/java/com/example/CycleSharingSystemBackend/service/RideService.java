package com.example.CycleSharingSystemBackend.service;

import com.example.CycleSharingSystemBackend.dto.*;
import com.example.CycleSharingSystemBackend.model.Ride;
import com.example.CycleSharingSystemBackend.model.RidePath;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RideService {
    Ride saveRideHistory(Ride rideHistory);


    RideDto updateRidePath(UpdateRidePathDto updateRidePathDto);

    List<RidePath> getRidePath(Long rideId);

    RideDto startRide(StartRideRequestDto startRideRequestDto);


    RideDto endRide(EndRideRequestDto endRideRequestDto);

    List<RideDto> getRideHistoryForUser(Long userId);
    List<RidePathSimpleDto> getActiveRidePaths();
    RidePathSimpleDto getCurrentLocation(Long rideId);

    RideDto getRideById(Long rideId);
    List<RideDto> getOnRideRides();

    long getTotalRides();
}
