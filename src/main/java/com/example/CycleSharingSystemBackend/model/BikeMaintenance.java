package com.example.CycleSharingSystemBackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.*;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BikeMaintenance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long maintenanceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bike_id")
    @JsonBackReference
    private Bikes bikes;

    private String type;
    private boolean status= true;
    private String comments;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate reportedDate;
}
