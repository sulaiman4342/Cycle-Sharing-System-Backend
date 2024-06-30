package com.example.CycleSharingSystemBackend.dto;

import lombok.Data;

@Data
public class StartRideRequestDto {
    private Long bikeId;
    private Long userId;
    private String startStationId;
}
