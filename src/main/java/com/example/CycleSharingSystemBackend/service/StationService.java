package com.example.CycleSharingSystemBackend.service;

import com.example.CycleSharingSystemBackend.dto.*;
import com.example.CycleSharingSystemBackend.dto.StationRequestDto;
import com.example.CycleSharingSystemBackend.model.Bikes;
import com.example.CycleSharingSystemBackend.model.Station;
import com.google.maps.errors.ApiException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public interface StationService {
    StationDto addStation(StationRequestDto stationRequestDto);

    List<StationDto> getAllStations();

    //    List<Station> findNearestStations(double latitude, double longitude, int radius) throws IOException, InterruptedException, ApiException;
    List<StationDistanceDTO> findNearestStations(double latitude, double longitude, int radius) throws IOException, InterruptedException, ApiException;

    Optional<StationDto> getStationById(String stationId);

    Station saveStation(Station station);

//    List<Bikes> getStationBikes(String stationId);
    

    int getAvailableBikeCount(String stationId);
    int getAvailableParkingSlots(String stationId);

    void addBikeToStation(Bikes bike, Station station);
    void removeBikeFromStation(Bikes bike, Station station);

    int getTotalAvailableBikes();

    List<BikeDto> getStationBikes(String stationId);


}