package com.example.CycleSharingSystemBackend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.*;
import java.util.List;

@Data
public class RideDto {
    private Long rideId;
    private Long userId;
    private String startStationId;
    private String endStationId;
    private boolean enRide;

    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    private Long bikeId;
    private List<RidePathDto> ridePaths;
//    private double hours;
//    private String Plan;
    private double estimatedAmount;
}
