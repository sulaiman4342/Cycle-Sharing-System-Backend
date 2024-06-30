package com.example.CycleSharingSystemBackend.dto;

import com.example.CycleSharingSystemBackend.model.Bikes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BikeRequestDTO {
    private String brand;
    private String bikeModel;
    private Long bikeCode;
    private String color;
    private String initStationId;
}