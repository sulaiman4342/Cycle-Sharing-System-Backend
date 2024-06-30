package com.example.CycleSharingSystemBackend.service;


import com.example.CycleSharingSystemBackend.dto.LocationDto;
import com.example.CycleSharingSystemBackend.dto.LocationRequestDto;
import com.example.CycleSharingSystemBackend.model.Location;
import org.springframework.stereotype.Service;

@Service
public interface LocationService {

    LocationDto addLocation(LocationRequestDto locationRequestDto);

    LocationDto getLocation(Double latitude, Double longitude);

    LocationDto findByLocationId(String locationId);
    public LocationDto updateLocation(LocationDto updatedLocationDto);
}