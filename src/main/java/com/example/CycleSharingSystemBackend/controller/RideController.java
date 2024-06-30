package com.example.CycleSharingSystemBackend.controller;

import com.example.CycleSharingSystemBackend.dto.*;
import com.example.CycleSharingSystemBackend.model.Ride;
import com.example.CycleSharingSystemBackend.model.RidePath;
import com.example.CycleSharingSystemBackend.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ride")
public class RideController {
    @Autowired
    private RideService rideHistoryService;

    @PostMapping("/add")
    public Ride addRideHistory(@RequestBody Ride rideHistory) {
        return rideHistoryService.saveRideHistory(rideHistory);
    }

    @PostMapping("/start")
    public ResponseEntity<RideDto> startRide(@RequestBody StartRideRequestDto startRideRequestDto) {
        RideDto rideDto = rideHistoryService.startRide(startRideRequestDto);
        return ResponseEntity.ok(rideDto);
    }

    @PostMapping("/end")
    public ResponseEntity<RideDto> endRide(@RequestBody EndRideRequestDto endRideRequestDto) {
        RideDto rideDto = rideHistoryService.endRide(endRideRequestDto);
        return ResponseEntity.ok(rideDto);
    }


    @PostMapping("/update-location")
    public ResponseEntity<RideDto> updateRideLocation(@RequestBody UpdateRidePathDto updateRidePathDto) {
        RideDto updatedRide = rideHistoryService.updateRidePath(updateRidePathDto);
        return ResponseEntity.ok(updatedRide);
    }

    @GetMapping("/{rideId}/path")
    public ResponseEntity<List<RidePath>> getRidePath(@PathVariable Long rideId) {
        List<RidePath> ridePath = rideHistoryService.getRidePath(rideId);
        return ResponseEntity.ok(ridePath);
    }


    @GetMapping("/history/{userId}")
    public ResponseEntity<List<RideDto>> getRideHistoryForUser(@PathVariable Long userId) {
        List<RideDto> rideHistory = rideHistoryService.getRideHistoryForUser(userId);
        if (rideHistory.isEmpty()) {
            return ResponseEntity.noContent().build(); // No content found
        } else {
            return ResponseEntity.ok(rideHistory);

        }


    }

    @GetMapping("/activeRidePaths")
    public List<RidePathSimpleDto> getActiveRidePaths() {
        return rideHistoryService.getActiveRidePaths();
    }
    @GetMapping("/{rideId}/currentLocation")
    public RidePathSimpleDto getCurrentLocation(@PathVariable Long rideId) {
        return rideHistoryService.getCurrentLocation(rideId);
    }
    @GetMapping("/{rideId}")
    public ResponseEntity<RideDto> getRideById(@PathVariable Long rideId) {
        RideDto rideDto = rideHistoryService.getRideById(rideId);
        return ResponseEntity.ok(rideDto);
    }
    @GetMapping("/onRide")
    public List<RideDto> getOnRideRides() {
        return rideHistoryService.getOnRideRides();
    }
    @GetMapping("/total")
    public long getTotalRides() {
        return rideHistoryService.getTotalRides();
    }
}
