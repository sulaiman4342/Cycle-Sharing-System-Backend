package com.example.CycleSharingSystemBackend.dto;

import com.example.CycleSharingSystemBackend.model.Station;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class StationDistanceDTO {
    private StationDto station;
    private double distance;
}
