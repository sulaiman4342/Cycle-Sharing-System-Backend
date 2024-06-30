package com.example.CycleSharingSystemBackend.service.impl;

import com.example.CycleSharingSystemBackend.dto.*;
import com.example.CycleSharingSystemBackend.model.Location;
import com.example.CycleSharingSystemBackend.model.User;
import com.example.CycleSharingSystemBackend.repository.UserRepository;
import com.example.CycleSharingSystemBackend.service.FareSettingsService;
import com.example.CycleSharingSystemBackend.service.LocationService;
import com.example.CycleSharingSystemBackend.service.UserService;
import com.example.CycleSharingSystemBackend.util.EmailUtil;
import com.example.CycleSharingSystemBackend.util.OtpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LocationService locationService;

    private final EmailUtil emailService;
    private final OtpUtil otpService;

    @Override
    public RegisterDto register(RegisterDto registerDto) {
        User existingUser = userRepository.findByEmail(registerDto.getEmail());
        String otp = otpService.generateOtp();
        if (existingUser != null && existingUser.isVerified()) {
            throw new RuntimeException("User already registered.");
        } else if (existingUser != null) {
            existingUser.setVerified(true);
//            existingUser.setRegisterTime(LocalDateTime.now());
            existingUser.setOtp(otp);
            userRepository.save(existingUser);
            emailService.sendVerificationEmail(existingUser.getEmail(), otp);
            return convertEntityToDto(existingUser);
        }

        User user = new User();
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setEmail(registerDto.getEmail());
        user.setMobile(registerDto.getMobile());
        user.setOtp(otp);
//        user.setRegisterTime(LocalDateTime.now());
        user.setVerified(false);
//        user.setInRide(false);
        userRepository.save(user);

        return convertEntityToDto(user);
    }

    @Override
    public UserDto login(String emailOrMobile) {
        User existingUser;
        if (emailOrMobile.contains("@")) {
            existingUser = userRepository.findByEmail(emailOrMobile);
        } else {
            existingUser = userRepository.findByMobile(emailOrMobile);
        }

        if (existingUser == null) {
            throw new RuntimeException("User is not registered.");
        } else if (!existingUser.isVerified()) {
            throw new RuntimeException("User is not verified.");
        } else {
            String otp = otpService.generateOtp();
            existingUser.setOtp(otp);
            userRepository.save(existingUser);
            emailService.sendVerificationEmail(existingUser.getEmail(), otp);
            return convertToDto(existingUser);
        }
    }

//    @Override
//    public void verify(VerificationRequestDTO verificationDTO) {
//        User user = userRepository.findByEmail(verificationDTO.getEmail());
//        if (user == null) {
//            throw new RuntimeException("User not found");
//        } else if (user.isVerified()) {
//            throw new RuntimeException("User is already verified");
//        } else if (verificationDTO.getOtp().equals(user.getOtp())) {
//            user.setVerified(true);
//            userRepository.save(user);
//        } else {
//            throw new RuntimeException("Internal Server Error");
//        }
//    }

    @Override
    public UserDto verify(String email, String otp) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        } else if (user.isVerified()) {
            throw new RuntimeException("User is already verified");
        } else if (otp.equals(user.getOtp())) {
            user.setVerified(true);
            userRepository.save(user);
            return convertToDto(user);
        } else {
            throw new RuntimeException("Internal Server Error");
        }
    }

//    @Override
//    public UserDto verifyLogin(LoginVerificationDTO loginDTO) {
//        User user = userRepository.findByEmail(loginDTO.getEmail());
//        if (user == null) {
//            throw new RuntimeException("User not found");
//        } else if (loginDTO.getOtp().equals(user.getOtp())) {
//            userRepository.save(user);
//            return convertToDto(user);
//        } else {
//            throw new RuntimeException("Internal Server Error");
//        }
//    }

    @Override
    public UserDto verifyLogin(String email, String otp) {

        User user = userRepository.findByEmail(email);
        if(user == null) {
            throw new RuntimeException("User not found");
        } else if (otp.equals(user.getOtp())) {
            userRepository.save(user);
            return convertToDto(user);
        } else {
            throw new RuntimeException("Internal Sever Error");
        }
    }



    @Override
    public List<User> getInRideUsers() {
        return userRepository.findByInRideTrue();
    }


    @Override
    public User updateUserLoc(UserLocationDTO userLocationDTO) throws Exception {
        User user = userRepository.findById(userLocationDTO.getUserId())
                .orElseThrow(() -> new Exception("User not found with ID: " + userLocationDTO.getUserId()));

        double latitude = userLocationDTO.getLatitude();
        double longitude = userLocationDTO.getLongitude();

        if (latitude >= -90 && latitude <= 90 && longitude >= -180 && longitude <= 180) {
            LocationDto existingLocationDto = locationService.getLocation(latitude, longitude);
            if (existingLocationDto != null) {
                user.setLocation(convertToEntity(existingLocationDto));
            } else {
                LocationDto newLocationDto = locationService.addLocation(new LocationRequestDto(latitude, longitude));
                user.setLocation(convertToEntity(newLocationDto));
            }
            userRepository.save(user);
        } else {
            throw new RuntimeException("Coordinates out of limit");
        }
        return user;
    }


    private Location convertToEntity(LocationDto locationDto) {
        Location location = new Location();
        location.setLocationId(locationDto.getLocationId());
        location.setLatitude(locationDto.getLatitude());
        location.setLongitude(locationDto.getLongitude());
        return location;
    }
    @Override
    public User UserLocationUpdate(Long userId, LocationRequestDto userLocationRequest) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("User not found with ID: " + userId));

        double latitude = userLocationRequest.getLatitude();
        double longitude = userLocationRequest.getLongitude();

        if (latitude >= -90 && latitude <= 90 && longitude >= -180 && longitude <= 180) {
            LocationDto existingLocationDto = locationService.getLocation(latitude, longitude);
            if (existingLocationDto != null) {
                user.setLocation(convertToEntity(existingLocationDto));
            } else {
                LocationDto newLocationDto = locationService.addLocation(userLocationRequest);
                user.setLocation(convertToEntity(newLocationDto));
            }
            userRepository.save(user);
        } else {
            throw new RuntimeException("Coordinates out of limit");
        }
        return user;
    }



    @Override
    public List<LiveRideUserDTO> getCurrentOnRideUsersWithLocation() {
        List<User> users = getInRideUsers();
        List<LiveRideUserDTO> liveRideUsers = new ArrayList<>();
        for (User u : users) {
            LocationDto location = locationService.findByLocationId(u.getLocation().getLocationId());
            LiveRideUserDTO userDTO = new LiveRideUserDTO();
            userDTO.setUserId(u.getUserId());
            userDTO.setLatitude(location.getLatitude());
            userDTO.setLongitude(location.getLongitude());
            liveRideUsers.add(userDTO);
        }
        return liveRideUsers;
    }

    @Override
    public int getNewUsersCountForToday() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime midnight = now.toLocalDate().atStartOfDay();
        Timestamp startTime = Timestamp.valueOf(midnight);
        Timestamp endTime = Timestamp.valueOf(now);
        return userRepository.countByRegisterTimeBetween(startTime, endTime);
    }

//    @Override
//    public int getNewUsersCountForPeriod(LocalDateTime endTime) {
//        LocalDateTime midnight = endTime.with(LocalTime.MIDNIGHT);
//        Timestamp startTime = Timestamp.valueOf(midnight);
////        return userRepository.countByRegisterTimeBetween(startTime, Timestamp.valueOf(endTime));
//    }

    @Override
    public int getTotalVerifiedUsers() {
        return userRepository.countByVerifiedTrue();
    }

    @Override
    public int getTotalOnRideUsers() {
        return userRepository.countByInRideTrue();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getInRideUsersByUserId(Long userId) {
        return userRepository.findByInRideTrueAndUserId(userId);
    }

    @Override
    public User getUserById(Long userId) throws Exception {
        return userRepository.findById(userId)
                .orElseThrow(() -> new Exception("User not found with ID: " + userId));
    }

    private RegisterDto convertEntityToDto(User user) {
        RegisterDto registerDto = new RegisterDto();
        registerDto.setFirstName(user.getFirstName());
        registerDto.setLastName(user.getLastName());
        registerDto.setEmail(user.getEmail());
        registerDto.setMobile(user.getMobile());
        return registerDto;
    }

    private UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setMobile(user.getMobile());
        userDto.setOtp(user.getOtp());
        userDto.setVerified(user.isVerified());
        userDto.setInRide(user.isInRide());
        userDto.setRegisterTime(user.getRegisterTime());
        return userDto;
    }


    @Autowired
    private FareSettingsService fareSettingsService;

    @Override
    public String getSubscriptionAmount(UserDto userDTO, String type, double hours) {

        switch (type) {
            case "DAILY":
                userDTO.setPlan("Daily");
                userDTO.setEstimatedAmount(fareSettingsService.getDailyRate());
            case "WEEKLLY":
                userDTO.setPlan("Weekly");
                userDTO.setEstimatedAmount(fareSettingsService.getWeeklyRate());
            case "MONTHLY":
                userDTO.setPlan("Monthly");
                userDTO.setEstimatedAmount(fareSettingsService.getMonthlyRate());
            default:
                double PayASYouGo=fareSettingsService.getHourlyRate()*hours;
                userDTO.setHours(hours);
                userDTO.setEstimatedAmount(PayASYouGo);
        }
        User user=new User();
        BeanUtils.copyProperties(userDTO,user);
        userRepository.save(user);
        return "success";
    }
}






