package com.api.parkingcontrol.repository;

import com.api.parkingcontrol.model.ParkingSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, UUID> {

    boolean existsByApartmentAndBlock(String apartment, String block);

}
