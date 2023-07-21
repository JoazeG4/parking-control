package com.api.parkingcontrol.dtos;

import com.api.parkingcontrol.model.ParkingSpot;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDto {

    @NotBlank
    private String responsableName;
    @NotBlank
    private String dateOfBirth;
    @NotBlank
    private String phone;
    @NotBlank
    private String cpf;
    private ParkingSpot parkingSpot;
}
