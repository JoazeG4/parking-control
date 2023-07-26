package com.api.parkingcontrol.service;

import com.api.parkingcontrol.dtos.CarDto;
import com.api.parkingcontrol.model.Car;
import com.api.parkingcontrol.repository.CarRepository;
import com.api.parkingcontrol.service.exceptions.EntityBadRequestException;
import com.api.parkingcontrol.service.exceptions.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CarService {

    final CarRepository carRepository;

    @Transactional
    public Car save(CarDto carDto){
        Car car = new Car();
        BeanUtils.copyProperties(carDto, car);
        car.setRegistrationDate(LocalDateTime.now());
        car.setUpdateDate(LocalDateTime.now());
        return carRepository.save(car);
    }

    public Car update(UUID id, CarDto carDto) throws EntityNotFoundException {
        var idCurrent = Optional.of(carRepository.findById(id).orElseThrow
                (() -> new EntityNotFoundException("Car not found.")));

        Car car = new Car();
        BeanUtils.copyProperties(carDto, car);
        car.setId(id);
        car.setUpdateDate(LocalDateTime.now());
        car.setRegistrationDate(idCurrent.get().getRegistrationDate());
        return carRepository.save(car);
    }

    public Page<Car> findAll(Pageable pageable) throws EntityBadRequestException {
        if(carRepository.findAll().isEmpty()) {
            throw new EntityBadRequestException("There are no registered cars.");
        }
        return carRepository.findAll(pageable);
    }

    public Car findById(UUID id) throws EntityNotFoundException {
        return carRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Car not found."));
    }

    public Car findByLicensePlateCar(String licensePlateCar) throws EntityNotFoundException {
        return carRepository.findByLicensePlateCar(licensePlateCar).
                orElseThrow(() -> new EntityNotFoundException("Car not found."));
    }

    public void deleteAll() throws EntityBadRequestException{
        if(carRepository.findAll().isEmpty()) {
            throw new EntityBadRequestException("There are no registered cars.");
        }
        carRepository.deleteAll();
    }

    @Transactional
    public void deleteById(UUID id) throws EntityNotFoundException {
        carRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Car not found."));
        carRepository.deleteById(id);
    }

    @Transactional
    public void deleteByLicensePlateCar(String licensePlateCar) throws EntityNotFoundException {
        carRepository.findByLicensePlateCar(licensePlateCar).orElseThrow(() -> new EntityNotFoundException("Car not found."));
        carRepository.deleteByLicensePlateCar(licensePlateCar);
    }
}
