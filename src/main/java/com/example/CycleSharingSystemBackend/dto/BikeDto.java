package com.example.CycleSharingSystemBackend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class BikeDto {
    private Long bikeId;
    private String brand;
    private String bikeModel;
    private Long bikeCode;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate lastMaintenanceDate;
    private String color;

    private boolean onRide = false;
    private String stationId;

}
