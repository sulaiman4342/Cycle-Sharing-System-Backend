package com.example.CycleSharingSystemBackend.repository;

import com.example.CycleSharingSystemBackend.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RideRepository extends JpaRepository<Ride, Long> {
    List<Ride> findByUserUserId(Long userId);
    List<Ride> findByInRide(boolean inRide);
    // Method to count the total number of rides
    @Query("SELECT COUNT(r) FROM Ride r")
    long countTotalRides();

}