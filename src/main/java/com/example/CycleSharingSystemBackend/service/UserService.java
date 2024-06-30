package com.example.CycleSharingSystemBackend.service;

import com.example.CycleSharingSystemBackend.dto.*;
import com.example.CycleSharingSystemBackend.model.Location;
import com.example.CycleSharingSystemBackend.model.User;
import com.example.CycleSharingSystemBackend.dto.UserDto;

import java.time.LocalDateTime;
import java.util.List;

public interface UserService {
    RegisterDto register(RegisterDto registerDto);

    UserDto login(String emailOrMobile);

//UserDto verify(VerificationRequestDTO verificationDTO);

//    User verifyLogin(LoginVerificationDTO loginDTO);
//UserDto verifyLogin(LoginVerificationDTO loginDTO);


    List<User> getInRideUsers();
    List<User> getInRideUsersByUserId(Long userId);

    User UserLocationUpdate(Long userId, LocationRequestDto userLocationRequest) throws Exception;

    public User updateUserLoc(UserLocationDTO userLocationDTO) throws Exception;


    List<LiveRideUserDTO> getCurrentOnRideUsersWithLocation();

    int getNewUsersCountForToday();
//
//    int getNewUsersCountForPeriod(LocalDateTime endTime);

    int getTotalVerifiedUsers();

    int getTotalOnRideUsers();
    List<User> getAllUsers();
    User getUserById(Long userId) throws Exception;

    public String getSubscriptionAmount(UserDto userDTO, String type, double hours);


    UserDto verify(String email, String otp);

    UserDto verifyLogin(String email, String otp);
}
