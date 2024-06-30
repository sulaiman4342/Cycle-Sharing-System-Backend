package com.example.CycleSharingSystemBackend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.*;

@Data
public class RidePathDto {
    private Long id;
    private Long rideId;
    private Double latitude;
    private Double longitude;

    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
}
