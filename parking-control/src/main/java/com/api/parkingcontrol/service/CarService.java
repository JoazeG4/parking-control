package com.api.parkingcontrol.service;

import com.api.parkingcontrol.dtos.CarDto;
import com.api.parkingcontrol.model.Car;
import com.api.parkingcontrol.repository.CarRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CarService {

    final CarRepository carRepository;

    @Transactional
    public Object save(CarDto carDto){
        Car car = new Car();
        BeanUtils.copyProperties(carDto, car);
        car.setRegistrationDate(LocalDateTime.now());
        car.setUpdateDate(LocalDateTime.now());
        return carRepository.save(car);
    }

    public Object update(UUID id, CarDto carDto) throws Exception {
        Car car = new Car();
        BeanUtils.copyProperties(carDto, car);

        var idCurrent = carRepository.findById(id);

        if(idCurrent.isPresent()) {
            car.setId(id);
            car.setUpdateDate(LocalDateTime.now());
            car.setRegistrationDate(idCurrent.get().getRegistrationDate());
            return carRepository.save(car);
        }
        throw new Exception("Car already in use.");
    }

    public List<Car> findAll() throws Exception {
        if(carRepository.findAll().isEmpty()) {
            throw new Exception("There are no registered cars.");
        }
        return carRepository.findAll();
    }

    public Object findById(UUID id) throws Exception {
        if (carRepository.findById(id).isEmpty()) {
            throw new Exception("Car not found.");
        }
        return carRepository.findById(id).get();
    }

    public void deleteAll() throws Exception{
        if(carRepository.findAll().isEmpty()) {
            throw new Exception("There are no registered cars.");
        }
        carRepository.deleteAll();
    }

    public void deleteById(UUID id) throws Exception {
        carRepository.findById(id).orElseThrow(() -> new Exception("Car not found."));
        carRepository.deleteById(id);
    }
}
