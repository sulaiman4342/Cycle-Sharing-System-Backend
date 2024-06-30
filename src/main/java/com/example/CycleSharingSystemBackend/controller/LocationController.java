package com.example.CycleSharingSystemBackend.controller;

import com.example.CycleSharingSystemBackend.dto.LocationDto;
import com.example.CycleSharingSystemBackend.dto.LocationRequestDto;
import com.example.CycleSharingSystemBackend.model.Location;
import com.example.CycleSharingSystemBackend.service.LocationService;
import com.example.CycleSharingSystemBackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/location")
public class LocationController {
    @Autowired
    private LocationService locationService;
    @Autowired
    private UserService userService;
    @PostMapping("/add")
    public ResponseEntity<LocationDto> addLocation(@RequestBody LocationRequestDto locationDto) {
        LocationDto savedLocation = locationService.addLocation(locationDto);
        return new ResponseEntity<>(savedLocation, HttpStatus.CREATED);
    }

    @GetMapping("/{latitude}/{longitude}")
    public LocationDto getLocation(@PathVariable Double latitude, @PathVariable Double longitude) {
        return locationService.getLocation(latitude,longitude);
    }
    @GetMapping("/{locationId}")
    public LocationDto getLocationById(@PathVariable String locationId) {
        return locationService.findByLocationId(locationId);
    }
    @PutMapping("/update")
    public ResponseEntity<LocationDto> updateLocation(@RequestBody LocationDto updatedLocationDto) {
        LocationDto updated = locationService.updateLocation(updatedLocationDto);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}