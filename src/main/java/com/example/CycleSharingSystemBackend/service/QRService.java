package com.example.CycleSharingSystemBackend.service;

import com.example.CycleSharingSystemBackend.model.Bikes;
import com.example.CycleSharingSystemBackend.dto.ResultDTO;

public interface QRService {
//    ResultDTO processQR();

    ResultDTO processQR(Bikes bike);
}
