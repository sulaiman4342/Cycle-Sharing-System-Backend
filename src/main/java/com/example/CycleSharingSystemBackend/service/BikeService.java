package com.example.CycleSharingSystemBackend.service;

import com.example.CycleSharingSystemBackend.dto.BikeDto;
import com.example.CycleSharingSystemBackend.dto.BikeRequestDTO;

import com.example.CycleSharingSystemBackend.model.Bikes;

import java.util.List;
import java.util.Optional;

public interface BikeService {
    BikeDto newBike(BikeRequestDTO NewBike);

    List<BikeDto> getAllBikes();

    Optional<BikeDto> getBikeById(Long bikeId);

    BikeDto updateBike(BikeDto newBike, Long bikeId);

    String deleteBike(Long bikeId);

    Long getLatestBikeId();

    int getTotalBikes();

    BikeDto getLatestBikeDetails();
}