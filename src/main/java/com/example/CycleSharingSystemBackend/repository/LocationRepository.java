package com.example.CycleSharingSystemBackend.repository;

import com.example.CycleSharingSystemBackend.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LocationRepository extends JpaRepository <Location, String> {
    Location findByLatitudeAndLongitude(Double latitude, Double longitude);

    Location findByLocationId(String locationId);
}