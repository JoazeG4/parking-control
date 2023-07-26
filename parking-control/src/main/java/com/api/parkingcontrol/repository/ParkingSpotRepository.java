package com.api.parkingcontrol.repository;

import com.api.parkingcontrol.model.ParkingSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, UUID> {

    boolean existsByApartmentAndBlock(int apartment, String block);
    Optional<ParkingSpot> findByApartment(int apartament);
    Optional<ParkingSpot> findByBlock(String block);
}
