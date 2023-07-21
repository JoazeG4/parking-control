package com.api.parkingcontrol.service;

import com.api.parkingcontrol.dtos.ParkingSpotDto;
import com.api.parkingcontrol.model.ParkingSpot;
import com.api.parkingcontrol.repository.ParkingSpotRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class ParkingSpotService {

    private ParkingSpotRepository parkingSpotRepository;

    @Transactional
    public Object save(ParkingSpotDto parkingSpotDto) throws Exception {
        ParkingSpot parkingSpot = new ParkingSpot();
        BeanUtils.copyProperties(parkingSpotDto, parkingSpot);
        parkingSpot.setUpdateDate(LocalDateTime.now());
        parkingSpot.setRegistrationDate(LocalDateTime.now());

        if(parkingSpotRepository.existsByApartmentAndBlock(parkingSpot.getApartment(), parkingSpot.getBlock())) {
            throw new Exception("Parking Spot Car already registered for this apartment/block.");
        }
        return parkingSpotRepository.save(parkingSpot);
    }

    public Object update(UUID id, ParkingSpotDto parkingSpotDto) throws Exception {
        ParkingSpot parkingSpot = new ParkingSpot();
        BeanUtils.copyProperties(parkingSpotDto, parkingSpot);

        var idCurrent = parkingSpotRepository.findById(id);
        var apartment = parkingSpotRepository.findByApartment(parkingSpot.getApartment());

        if(idCurrent.isPresent()) {
            if(Objects.equals(idCurrent.get().getApartment(), parkingSpot.getApartment()) || apartment.isEmpty()){
                parkingSpot.setId(id);
                parkingSpot.setUpdateDate(LocalDateTime.now());
                parkingSpot.setRegistrationDate(idCurrent.get().getRegistrationDate());
                return parkingSpotRepository.save(parkingSpot);
            }
            throw new Exception("Parking spot number is already in use.");
        }
        throw new Exception("Apartment is already in use.");
    }

    public List<ParkingSpot> findAll() throws Exception {
        if(parkingSpotRepository.findAll().isEmpty()) {
            throw new Exception("Empty parking lot.");
        }
        return parkingSpotRepository.findAll();
    }

    public Object findById(UUID id) throws Exception {
        if (parkingSpotRepository.findById(id).isEmpty()) {
            throw new Exception("Parking Spot not found.");
        }
        return parkingSpotRepository.findById(id).get();
    }

    public Object deleteAll() throws Exception{
        if(parkingSpotRepository.findAll().isEmpty()) {
            throw new Exception("Empty parking lot.");
        }
        parkingSpotRepository.deleteAll();
        return null;
    }

    public void deleteById(UUID id) throws Exception {
        parkingSpotRepository.findById(id).orElseThrow(() -> new Exception("Parking Spot not found."));
        parkingSpotRepository.deleteById(id);
    }
}

