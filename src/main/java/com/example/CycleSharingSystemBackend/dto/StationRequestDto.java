package com.example.CycleSharingSystemBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StationRequestDto {
    private String name;
    private LocationRequestDto location;
    private int capacity;
}
