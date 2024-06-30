package com.example.CycleSharingSystemBackend.exception;

public class BikeNotFoundException extends RuntimeException{
    public  BikeNotFoundException(Long Bike_id){
        super("Could not found the user with id"+Bike_id);
    }
}
