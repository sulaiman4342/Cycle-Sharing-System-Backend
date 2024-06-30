package com.example.CycleSharingSystemBackend.service.impl;

import com.example.CycleSharingSystemBackend.dto.*;
import com.example.CycleSharingSystemBackend.dto.StationRequestDto;
import com.example.CycleSharingSystemBackend.model.Bikes;
import com.example.CycleSharingSystemBackend.model.Location;
import com.example.CycleSharingSystemBackend.model.Station;
import com.example.CycleSharingSystemBackend.repository.Stationrepository;
import com.example.CycleSharingSystemBackend.service.LocationService;
import com.example.CycleSharingSystemBackend.service.StationService;
import com.google.maps.errors.ApiException;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class StationServicelmpl implements StationService {
    private static final Logger logger = Logger.getLogger(StationServicelmpl.class.getName());
    @Value("${google.maps.api.key}")
    private String apiKey;
    @Autowired
    private Stationrepository stationRepository;
    @Autowired
    private LocationService locationService;
    private final AtomicLong currentId = new AtomicLong(0);
    private String generateStationId() {
        return "ST" + currentId.incrementAndGet();
    }


    @Override
    public StationDto addStation(StationRequestDto stationRequestDto) {
        logger.info("Adding a new station");
        LocationRequestDto locationRequestDto = stationRequestDto.getLocation();
        LocationDto savedLocationDto = locationService.addLocation(locationRequestDto);

        Station station = new Station();
        station.setName(stationRequestDto.getName());
        station.setLocation(convertToEntity(savedLocationDto));
        station.setCapacity(stationRequestDto.getCapacity());
        station.setStationId(generateStationId());

        Station savedStation = stationRepository.save(station);
        logger.info("Exiting method XYZ");

        return convertToDto(savedStation);
    }
    private Location convertToEntity(LocationDto locationDto) {
        Location location = new Location();
        location.setLocationId(locationDto.getLocationId());
        location.setLatitude(locationDto.getLatitude());
        location.setLongitude(locationDto.getLongitude());
        return location;
    }

    private StationDto convertToDto(Station station) {
        Location location = station.getLocation();


        LocationDto locationDto = new LocationDto(location.getLocationId(), location.getLatitude(), location.getLongitude());

        return StationDto.builder()
                .stationId(station.getStationId())
                .name(station.getName())
                .location(locationDto)
                .capacity(station.getCapacity())
                .availableBike(station.getAvailableBike())
                .availableParkingSlots(station.getAvailableParkingSlots())
                .build();
    }

    @Override
    public List<StationDto> getAllStations() {
        List<Station> stations = stationRepository.findAll();
        List<StationDto> stationDtos = new ArrayList<>();

        for (Station station : stations) {
            stationDtos.add(convertToDto(station));
        }

        return stationDtos;
    }


    @Override
    public List<StationDistanceDTO> findNearestStations(double latitude, double longitude, int radius) throws IOException, InterruptedException, ApiException {

        // Radius of the Earth in kilometers
        final double earthRadius = 6371;

        // Convert latitude and longitude from degrees to radians
        double lat1 = Math.toRadians(latitude);
        double lon1 = Math.toRadians(longitude);

        List<StationDto> allStations = getAllStations();
        Map<StationDto, Double> stationDistances = new HashMap<>();

        for (StationDto station : allStations) {
            double lat2 = Math.toRadians(station.getLocation().getLatitude());
            double lon2 = Math.toRadians(station.getLocation().getLongitude());

            // Haversine formula
            double dlon = lon2 - lon1;
            double dlat = lat2 - lat1;
            double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            double distance = earthRadius * c;

            // Check if the station is within the given radius
            if (distance <= radius) {
                stationDistances.put(station, distance);
            }
        }

        // Sort stations by distance
        List<Map.Entry<StationDto, Double>> sortedStations = new ArrayList<>(stationDistances.entrySet());
        sortedStations.sort(Map.Entry.comparingByValue());

        List<StationDistanceDTO> nearestStations = new ArrayList<>();
        for (Map.Entry<StationDto, Double> entry : sortedStations) {
            StationDto station = entry.getKey();
            Double distanceKilometers = entry.getValue();
            double distanceMeters = Math.round(distanceKilometers * 1000 * 100.0) / 100.0;
            nearestStations.add(new StationDistanceDTO(station, distanceMeters));
        }

        return nearestStations;
    }



    @Override
    public Optional<StationDto> getStationById(String stationId) {
        Optional<Station> stationOptional = stationRepository.findByStationId(stationId);
        return stationOptional.map(this::convertToDto);
    }




    @Override
    public Station saveStation(Station station) {
        return stationRepository.save(station);
    }



    @Override
    public int getAvailableBikeCount(String stationId) {
        Optional<Station> stationOptional = stationRepository.findByStationId(stationId);
        return stationOptional.map(Station::getAvailableBike).orElse(0);
    }

    @Override
    public int getAvailableParkingSlots(String stationId) {
        Optional<Station> stationOptional = stationRepository.findByStationId(stationId);
        return stationOptional.map(station -> station.getCapacity() - station.getAvailableBike()).orElse(0);
    }

    @Override
    public void addBikeToStation(Bikes bike, Station station) {
        if (!station.getBikes().contains(bike)) { // Check if the bike is already in the station's list of bikes
            station.getBikes().add(bike);
            bike.setStation(station);
            saveStation(station);
        }
    }
    @Override
    public void removeBikeFromStation(Bikes bike, Station station) {
        if (station.getBikes().contains(bike)) { // Check if the bike is in the station's list of bikes
            station.getBikes().remove(bike);
            bike.setStation(null);
            saveStation(station);
        }
    }
    @Override
    public int getTotalAvailableBikes() {
        List<Station> allStations = stationRepository.findAll();
        return allStations.stream()
                .mapToInt(Station::getAvailableBike)
                .sum();
    }



    @Override
    public List<BikeDto> getStationBikes(String stationId) {
        Optional<Station> station = stationRepository.findById(stationId);
        if (station.isPresent()) {
            return convertToBikeDtoList(station.get().getBikes());
        }
        return null;
    }




    private List<BikeDto> convertToBikeDtoList(List<Bikes> bikes) {
        return bikes.stream()
                .map(this::convertToBikeDto)
                .collect(Collectors.toList());
    }
    private BikeDto convertToBikeDto(Bikes bike) {
        return BikeDto.builder()
                .bikeId(bike.getBikeId())
                .brand(bike.getBrand())
                .bikeModel(bike.getBikeModel())
                .bikeCode(bike.getBikeCode())
                .lastMaintenanceDate(bike.getLastMaintenanceDate())
                .color(bike.getColor())
                .onRide(bike.isOnRide())
                .stationId(bike.getStation() != null ? bike.getStation().getStationId() : null)
                .build();
    }

}