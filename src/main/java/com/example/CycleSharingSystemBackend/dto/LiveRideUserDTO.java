package com.example.CycleSharingSystemBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LiveRideUserDTO {
    private Long userId;
    private double latitude;
    private double longitude;
}
