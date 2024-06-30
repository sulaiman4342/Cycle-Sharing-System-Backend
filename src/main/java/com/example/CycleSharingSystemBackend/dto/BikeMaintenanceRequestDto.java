package com.example.CycleSharingSystemBackend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BikeMaintenanceRequestDto {
    private Long bikeId;
    private String type;
    private String comments;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate reportedDate;
}