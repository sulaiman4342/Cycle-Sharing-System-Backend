package com.example.CycleSharingSystemBackend.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class UserLocationDTO {
    private Long userId;
    private double latitude;
    private double longitude;
}
