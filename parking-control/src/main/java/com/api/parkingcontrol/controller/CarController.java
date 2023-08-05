package com.api.parkingcontrol.controller;

import com.api.parkingcontrol.dtos.CarDto;
import com.api.parkingcontrol.model.Car;
import com.api.parkingcontrol.service.CarService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/cars")
public class CarController {

    final CarService carService;

    @PostMapping
    public ResponseEntity<Car> saveCar(@RequestBody @Valid CarDto carDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(carService.save(carDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable UUID id, @RequestBody @Valid CarDto carDto){
        return ResponseEntity.status(HttpStatus.OK).body(carService.update(id, carDto));
    }

    @GetMapping
    public ResponseEntity<Page<Car>> getAllCar(@PageableDefault(page = 0,
            size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(carService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getOneCar(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(carService.findById(id));
    }

    @GetMapping("licensePlateCar/{licensePlateCar}")
    public ResponseEntity<Car> getOneLicensePlateCar(@PathVariable String licensePlateCar){
        return ResponseEntity.status(HttpStatus.OK).body(carService.findByLicensePlateCar(licensePlateCar));
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteAllCar(){
        carService.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).body("Cars deleted successfully.");
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<Object> deleteOneCar(@PathVariable UUID id){
        carService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Car deleted successfully.");
    }

    @DeleteMapping("licensePlateCar/{licensePlateCar}")
    public ResponseEntity<Object> deleteLicensePlateCar(@PathVariable String licensePlateCar){
        carService.deleteByLicensePlateCar(licensePlateCar);
        return ResponseEntity.status(HttpStatus.OK).body("Car deleted successfully.");
    }
}