package com.example.CycleSharingSystemBackend.repository;

import com.example.CycleSharingSystemBackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.security.Timestamp;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(String email);

    User findByMobile(String emailOrMobile);

    List<User> findByInRideTrue();

    List<User> findByInRideTrueAndUserId(Long userId);

//    int countByRegistrationTimeBetween(Timestamp startTime, Timestamp endTime);

    int countByVerifiedTrue();
    int countByInRideTrue();

    int countByRegisterTimeBetween(java.sql.Timestamp startTime, java.sql.Timestamp endTime);
}
