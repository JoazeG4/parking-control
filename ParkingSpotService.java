package com.api.parkingcontrol.service;

import com.api.parkingcontrol.dtos.ParkingSpotDto;
import com.api.parkingcontrol.model.ParkingSpot;
import com.api.parkingcontrol.repository.ParkingSpotRepository;
import com.api.parkingcontrol.service.exceptions.EntityBadRequestException;
import com.api.parkingcontrol.service.exceptions.EntityConflitException;
import com.api.parkingcontrol.service.exceptions.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class ParkingSpotService {

    private ParkingSpotRepository parkingSpotRepository;

    @Transactional
    public ParkingSpot save(ParkingSpotDto parkingSpotDto) throws EntityConflitException {
        ParkingSpot parkingSpot = new ParkingSpot();
        BeanUtils.copyProperties(parkingSpotDto, parkingSpot);
        parkingSpot.setUpdateDate(LocalDateTime.now());
        parkingSpot.setRegistrationDate(LocalDateTime.now());

        if(parkingSpotRepository.existsByApartmentAndBlock(parkingSpot.getApartment(), parkingSpot.getBlock())) {
            throw new EntityConflitException("Parking Spot Car already registered for this apartment/block.");
        }
        return parkingSpotRepository.save(parkingSpot);
    }

    public ParkingSpot update(UUID id, ParkingSpotDto parkingSpotDto) throws EntityNotFoundException {
        var idCurrent = Optional.of(parkingSpotRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Parking spot not found.")));

        if (!Objects.equals(idCurrent.get().getApartment(), parkingSpotDto.getApartment()) || !Objects.equals(idCurrent.get().getBlock(), parkingSpotDto.getBlock())){
            if (parkingSpotRepository.existsByApartmentAndBlock(parkingSpotDto.getApartment(), parkingSpotDto.getBlock())) {
                throw new EntityConflitException("Parking Spot Car already registered for this apartment/block.");
            }
        }
        ParkingSpot parkingSpot = new ParkingSpot();
        BeanUtils.copyProperties(parkingSpotDto, parkingSpot);
        parkingSpot.setId(id);
        parkingSpot.setUpdateDate(LocalDateTime.now());
        parkingSpot.setRegistrationDate(idCurrent.get().getRegistrationDate());
        return parkingSpotRepository.save(parkingSpot);
    }

    public Page<ParkingSpot> findAll(Pageable pageable) throws EntityBadRequestException {
        if(parkingSpotRepository.findAll().isEmpty()) {
            throw new EntityBadRequestException("Empty parking lot.");
        }
        return parkingSpotRepository.findAll(pageable);
    }

    public ParkingSpot findById(UUID id) throws EntityNotFoundException {
        return parkingSpotRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Parking Spot not found."));
    }

    public void deleteAll() throws EntityBadRequestException {
        if(parkingSpotRepository.findAll().isEmpty()){
            throw new EntityBadRequestException("Empty parking lot.");
        }
        parkingSpotRepository.deleteAll();
    }

    @Transactional
    public void deleteById(UUID id) throws EntityNotFoundException {
        parkingSpotRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Parking Spot not found."));
        parkingSpotRepository.deleteById(id);
    }
}

