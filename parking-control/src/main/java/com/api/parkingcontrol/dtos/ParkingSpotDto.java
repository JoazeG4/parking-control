package com.api.parkingcontrol.dtos;

import com.api.parkingcontrol.model.Car;
import com.api.parkingcontrol.model.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ParkingSpotDto {

    @NotBlank
    private String parkingSpotNumber;
    @NotBlank
    private String apartment;
    @NotBlank
    private String block;
    private User user;
    private Car car;
}
