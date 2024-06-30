package com.example.CycleSharingSystemBackend.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Data
public class FareSettings {
    @Id


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double hourlyRate;
    private double dailyRate;
    private double weeklyRate;
    private double monthlyRate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}