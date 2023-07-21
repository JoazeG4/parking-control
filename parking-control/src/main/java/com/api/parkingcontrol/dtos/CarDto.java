package com.api.parkingcontrol.dtos;

import com.api.parkingcontrol.model.ParkingSpot;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CarDto {

    @NotBlank
    private String licensePlateCar;
    @NotBlank
    private String brandCar;
    @NotBlank
    private String modelCar;
    @NotBlank
    private String colorCar;
    private ParkingSpot parkingSpot;
}
