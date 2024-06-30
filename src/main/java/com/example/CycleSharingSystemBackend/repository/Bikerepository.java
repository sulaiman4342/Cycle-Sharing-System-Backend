package com.example.CycleSharingSystemBackend.repository;

import com.example.CycleSharingSystemBackend.model.Bikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface Bikerepository extends JpaRepository<Bikes, Long> {
    @Query("SELECT MAX(b.bikeId) FROM Bikes b")
    Long findLatestBikeId();

    @Query(value = "SELECT b FROM Bikes b WHERE b.bikeId = (SELECT MAX(b2.bikeId) FROM Bikes b2)")
    Bikes findLatestBikeDetails();
}
