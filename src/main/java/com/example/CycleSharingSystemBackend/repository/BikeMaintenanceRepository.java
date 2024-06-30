package com.example.CycleSharingSystemBackend.repository;
import com.example.CycleSharingSystemBackend.model.BikeMaintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BikeMaintenanceRepository extends JpaRepository<BikeMaintenance,Long> {
    @Query("SELECT COUNT(DISTINCT b.bikes.bikeId) FROM BikeMaintenance b WHERE b.status = true")
    Long countDistinctBikeIdsInMaintenance();
}
