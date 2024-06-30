package com.example.CycleSharingSystemBackend.controller;

import com.example.CycleSharingSystemBackend.dto.*;
import com.example.CycleSharingSystemBackend.dto.StationRequestDto;
import com.example.CycleSharingSystemBackend.model.Bikes;
import com.example.CycleSharingSystemBackend.model.Location;
import com.example.CycleSharingSystemBackend.model.Station;
import com.example.CycleSharingSystemBackend.repository.Stationrepository;
import com.example.CycleSharingSystemBackend.service.LocationService;
import com.example.CycleSharingSystemBackend.service.StationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/v1/station")
@Slf4j
public class StationController {

    @Autowired
    private StationService stationService;
    @Autowired
    private LocationService locationService;

    @PostMapping("/add")
    public ResponseEntity<StationDto> newStation(@RequestBody StationRequestDto stationRequestDto) {
        try {
            StationDto newStation = stationService.addStation(stationRequestDto);
            return ResponseEntity.ok(newStation);
        } catch (RuntimeException e) {
            log.error("Add failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/all")
    public List<StationDto> getAllStations() {
        return stationService.getAllStations();
    }



    @GetMapping("/nearest")
    public ResponseEntity<List<StationDistanceDTO>> findNearestStations(@RequestParam double latitude, @RequestParam double longitude, @RequestParam int radius) {
        try {
            List<StationDistanceDTO> nearestStations = stationService.findNearestStations(latitude, longitude, radius);
            return ResponseEntity.ok(nearestStations);
        } catch (Exception e) {
            log.error("failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{stationId}/available-bikes")
    public ResponseEntity<Integer> getAvailableBikeCount(@PathVariable String stationId) {
        int availableBikeCount = stationService.getAvailableBikeCount(stationId);
        return ResponseEntity.ok(availableBikeCount);
    }

    @GetMapping("/{stationId}/available-parking-slots")
    public ResponseEntity<Integer> getAvailableParkingSlots(@PathVariable String stationId) {
        int availableParkingSlots = stationService.getAvailableParkingSlots(stationId);
        return ResponseEntity.ok(availableParkingSlots);
    }
    @GetMapping("/total-available-bikes")
    public ResponseEntity<Integer> getTotalAvailableBikes() {
        int totalAvailableBikes = stationService.getTotalAvailableBikes();
        return ResponseEntity.ok(totalAvailableBikes);
    }



    @GetMapping("/bikes/{stationId}")
    public List<BikeDto> getStationBikes(@PathVariable String stationId) {
        return stationService.getStationBikes(stationId);
    }




}






