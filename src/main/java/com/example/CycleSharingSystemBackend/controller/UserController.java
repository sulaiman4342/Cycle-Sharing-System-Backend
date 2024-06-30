package com.example.CycleSharingSystemBackend.controller;

import com.example.CycleSharingSystemBackend.dto.*;
import com.example.CycleSharingSystemBackend.model.Location;
import com.example.CycleSharingSystemBackend.model.User;
import com.example.CycleSharingSystemBackend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("api/v1/user")
@RestController
public class UserController {
    @Autowired
    private UserService userService;



    @PostMapping("/register")
    public ResponseEntity<RegisterDto> register(@RequestBody RegisterDto registerDto) {
        RegisterDto response = userService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginDto loginDto) {
        String emailOrMobile = loginDto.getEmailOrMobile();
        UserDto userDto = userService.login(emailOrMobile);
        return ResponseEntity.ok(userDto);
    }



    @PostMapping("/verify")
    public ResponseEntity<UserDto> verifiedUser(@RequestParam String email, @RequestParam String otp) {
        try {
            UserDto verifiedUser = userService.verify(email,otp);
            return ResponseEntity.ok(verifiedUser);
        } catch(RuntimeException e) {
            log.error("Verification failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);        }
    }


    @PostMapping("/verifyLogin")
    public ResponseEntity<UserDto> verifiedUserLogin(@RequestParam String email, @RequestParam String otp) {
        try {
            UserDto user = userService.verifyLogin(email,otp);
            return ResponseEntity.ok(user);
        } catch(RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


    @PutMapping("/update/location/{userId}")
    public User UserLocationUpdate(@PathVariable Long userId, @RequestBody LocationRequestDto userLocationRequest) throws Exception {
        return userService.UserLocationUpdate(userId, userLocationRequest);
    }

    @PutMapping("/update")
    public User updateUser(@RequestBody UserLocationDTO userLocationDTO) throws Exception {
        return userService.updateUserLoc(userLocationDTO);
    }


    @GetMapping("/inRide")
    public List<User> GetInRideUsers() {

        return userService.getInRideUsers();
    }

//    @GetMapping("/in-ride-locations")
//    public ResponseEntity<List<UserLocation>> getInRideUserLocations() {
//        List<UserLocation> inRideUserLocations = userService.getInRideUserLocations();
//        return ResponseEntity.ok(inRideUserLocations);
//    }


    @GetMapping("/onRideUsers")
    public List<LiveRideUserDTO> getCurrentOnRideUsersWithLocation() {
        return userService.getCurrentOnRideUsersWithLocation();
    }

//    @GetMapping("/getNewUsersCountForToday")
//    public ResponseEntity<Integer> updateNewUsersCount() {
//        int newUsersCount = userService.getNewUsersCountForToday();
//        return ResponseEntity.ok(newUsersCount);
//    }

    @GetMapping("/totalVerifiedUsers")
    public ResponseEntity<Integer> getTotalVerifiedUsers() {
        int totalVerifiedUsers = userService.getTotalVerifiedUsers();
        return ResponseEntity.ok(totalVerifiedUsers);
    }

    @GetMapping("/totalonRideUsers")
    public ResponseEntity<Integer> getTotalOnRideUsers() {
        int totalOnRideUsers = userService.getTotalOnRideUsers();
        return ResponseEntity.ok(totalOnRideUsers);
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> allUsers = userService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("/inRide/{userId}")
    public ResponseEntity<List<User>> getInRideUsersByUserId(@PathVariable Long userId) {
        List<User> inRideUsers = userService.getInRideUsersByUserId(userId);
        return new ResponseEntity<>(inRideUsers, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        try {
            User user = userService.getUserById(userId);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}






