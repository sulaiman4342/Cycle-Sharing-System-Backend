package com.example.CycleSharingSystemBackend.controller;

import com.example.CycleSharingSystemBackend.model.Bikes;
import com.example.CycleSharingSystemBackend.dto.ResultDTO;
import com.example.CycleSharingSystemBackend.repository.Bikerepository;
import com.example.CycleSharingSystemBackend.service.QRService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000")
@RestController
public class QRController {
    @Autowired
    private QRService qrService;

    @Autowired
    private Bikerepository bikeRepository;

    @GetMapping("/qr-generator/{bikeId}")
    public String processQR(@PathVariable Long bikeId) {
        try {
            Bikes bike = bikeRepository.findById(bikeId).orElse(null);
            if (bike != null) {
                ResultDTO result = qrService.processQR(bike);
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.writeValueAsString(result);
            } else {
                return "Bike not found";
            }
        } catch (Exception e) {
            return "Exception found: " + e.getMessage();
        }
    }
}
