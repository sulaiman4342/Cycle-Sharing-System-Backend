package com.example.CycleSharingSystemBackend.dto;

import lombok.Data;

@Data
public class UpdateRidePathDto {
    private Long rideId;
    private Double latitude;
    private Double longitude;
}
