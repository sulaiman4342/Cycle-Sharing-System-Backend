package com.example.CycleSharingSystemBackend.service.impl;

import com.example.CycleSharingSystemBackend.dto.LocationDto;
import com.example.CycleSharingSystemBackend.dto.LocationRequestDto;
import com.example.CycleSharingSystemBackend.model.Location;
import com.example.CycleSharingSystemBackend.repository.LocationRepository;
import com.example.CycleSharingSystemBackend.service.LocationService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.concurrent.atomic.AtomicLong;

@Service
public class LocationServicelmpl implements LocationService {
    @Autowired
    private LocationRepository locationRepository;
    private static final AtomicLong counter = new AtomicLong(System.currentTimeMillis());

    public static String generateLocationId() {
        return "LOC-" + counter.getAndIncrement();
    }
    @Override
    public LocationDto addLocation(LocationRequestDto locationRequestDto) {
        Location location = new Location();
        location.setLocationId(generateLocationId());
        location.setLatitude(locationRequestDto.getLatitude());
        location.setLongitude(locationRequestDto.getLongitude());
        Location savedLocation = locationRepository.save(location);
        return convertToDto(savedLocation);
    }

    @Override
    public LocationDto getLocation(Double latitude, Double longitude) {
        Location location = locationRepository.findByLatitudeAndLongitude(latitude, longitude);
      if (location == null) {
          return null;
        }
        return convertToDto(location);
    }

    @Override
    public LocationDto findByLocationId(String locationId) {
        Location location = locationRepository.findByLocationId(locationId);
        if (location == null) {
            throw new EntityNotFoundException("Location not found");
        }
        return convertToDto(location);
    }


    @Override
    public LocationDto updateLocation(LocationDto updatedLocationDto) {
        Location location = locationRepository.findByLocationId(updatedLocationDto.getLocationId());
        if (location == null) {
            throw new EntityNotFoundException("Location not found");
        }
        location.setLatitude(updatedLocationDto.getLatitude());
        location.setLongitude(updatedLocationDto.getLongitude());
        Location updatedLocation = locationRepository.save(location);
        return convertToDto(updatedLocation);
    }
    private LocationDto convertToDto(Location location) {
        return new LocationDto(location.getLocationId(), location.getLatitude(), location.getLongitude());
    }
}