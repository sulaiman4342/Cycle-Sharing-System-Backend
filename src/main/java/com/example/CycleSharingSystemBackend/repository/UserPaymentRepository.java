package com.example.CycleSharingSystemBackend.repository;
import com.example.CycleSharingSystemBackend.model.User;
import com.example.CycleSharingSystemBackend.model.UserPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface UserPaymentRepository extends JpaRepository<UserPayment, Long> {

    @Query("SELECT SUM(up.estimatedAmount) FROM UserPayment up WHERE up.paymentDate = :paymentDate")
    Double getTotalEstimatedAmountForDate(@Param("paymentDate") LocalDate paymentDate);


    @Query("SELECT SUM(up.estimatedAmount) FROM UserPayment up WHERE up.paymentDate = :date")
    Double findDailySum(@Param("date") LocalDate date);

    @Query("SELECT SUM(up.estimatedAmount) FROM UserPayment up WHERE YEAR(up.paymentDate) = :year AND MONTH(up.paymentDate) = :month")
    Double findMonthlySum(@Param("year") int year, @Param("month") int month);
}
