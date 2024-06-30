package com.example.CycleSharingSystemBackend.repository;

import com.example.CycleSharingSystemBackend.model.Ride;
import com.example.CycleSharingSystemBackend.model.RidePath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface RidePathRepository extends JpaRepository<RidePath, Long> {
    List<RidePath> findByRideOrderByTimestamp(Ride ride);
}
