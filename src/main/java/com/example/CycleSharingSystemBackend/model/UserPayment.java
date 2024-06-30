package com.example.CycleSharingSystemBackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.*;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_payments")
public class UserPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary key for UserPayment

    private Double estimatedAmount;

    @JsonFormat( pattern = "yyyy-MM-dd")
    private LocalDate paymentDate;

    @OneToOne
    @JoinColumn(name= "ride_id")
    private Ride ride;
}
