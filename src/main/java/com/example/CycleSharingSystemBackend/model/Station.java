package com.example.CycleSharingSystemBackend.model;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Station {
    @Id
    private String stationId;
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "locationId")
    private Location location;

    private int capacity;

    @OneToMany(mappedBy = "station", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Bikes> bikes = new ArrayList<>(); // Initialize bikes list

    @OneToMany(mappedBy = "startStation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Ride> startRides;

    @OneToMany(mappedBy = "endStation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Ride> endRides;

    public int getAvailableBike() {
        return bikes != null ? bikes.size() : 0;
    }

    public int getAvailableParkingSlots() {
        int availableBikes = getAvailableBike();
        return capacity - availableBikes;
    }
}
