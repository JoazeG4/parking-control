package com.api.parkingcontrol.repository;

import com.api.parkingcontrol.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CarRepository extends JpaRepository<Car, UUID> {

    boolean existsByLicensePlateCar(String licensePlateCar);

    Optional<Car> findByLicensePlateCar(String licensePlateCar);
}
