package com.example.CycleSharingSystemBackend.dto;

import com.example.CycleSharingSystemBackend.model.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StationDto {
    private String stationId;
    private String name;
    private LocationDto location;
    private int capacity;
    private int availableBike;
    private int availableParkingSlots;


}
