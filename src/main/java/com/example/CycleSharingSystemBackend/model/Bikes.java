package com.example.CycleSharingSystemBackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.time.*;
import java.util.List;
import java.util.Objects;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name= "Bikes")
public class Bikes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bikeId;
    private String brand;
    private String bikeModel;
    private Long bikeCode;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate lastMaintenanceDate = LocalDate.now();
    private String color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id")
    @JsonBackReference
    private Station station;

    @Transient
    private String initStationId;
    private boolean onRide = false;

    @OneToMany(mappedBy = "bikes", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<BikeMaintenance> maintenanceRecords;

    @OneToMany(mappedBy = "bike", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Ride> rides;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bikes bikes = (Bikes) o;
        return Objects.equals(bikeId, bikes.bikeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bikeId);
    }
}
