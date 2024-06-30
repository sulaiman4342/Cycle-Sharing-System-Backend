package com.example.CycleSharingSystemBackend.controller;


import com.example.CycleSharingSystemBackend.dto.BikeDto;
import com.example.CycleSharingSystemBackend.dto.BikeRequestDTO;
import com.example.CycleSharingSystemBackend.model.Bikes;
import com.example.CycleSharingSystemBackend.service.BikeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("api/v1/Bikes")
public class BikeController {

    @Autowired
    private BikeService bikeService;

    @PostMapping
    public BikeDto newBikes(@RequestBody BikeRequestDTO newBikeDto){

        return bikeService.newBike(newBikeDto);
    }

    @GetMapping
    public List<BikeDto> getAllBikes() {
        return bikeService.getAllBikes();
    }

    @GetMapping("/{bikeId}")
    public ResponseEntity<BikeDto> getBikeById(@PathVariable Long bikeId) {
        Optional<BikeDto> bikeOptional = bikeService.getBikeById(bikeId);
        if (bikeOptional.isPresent()) {
            return ResponseEntity.ok(bikeOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @PutMapping("/{bikeId}")
    public BikeDto updateBike(@RequestBody BikeDto newBike, @PathVariable Long bikeId) {
        return bikeService.updateBike(newBike, bikeId);
    }

    @DeleteMapping("/{bikeId}")
    public String deleteBike(@PathVariable Long bikeId) {
        return bikeService.deleteBike(bikeId);
    }

    @GetMapping("/latestBikeId")
    public Long getLatestBikeId() {
        return bikeService.getLatestBikeId();
    }

    @GetMapping("/count")
    public int getTotalBikes() {
        return bikeService.getTotalBikes();
    }

    @GetMapping("/latestBikeDetails")
    public BikeDto getLatestBikeDetails() {
        return bikeService.getLatestBikeDetails();
    }
}
