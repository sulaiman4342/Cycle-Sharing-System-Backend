package com.example.CycleSharingSystemBackend.dto;

import lombok.Data;

@Data
public class EndRideRequestDto {
    private Long rideId;
    private String endStationId;
}