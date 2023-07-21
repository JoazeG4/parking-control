package com.api.parkingcontrol.controller;

import com.api.parkingcontrol.dtos.CarDto;
import com.api.parkingcontrol.model.Car;
import com.api.parkingcontrol.service.CarService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/cars")
public class CarController {

    final CarService carService;

    @PostMapping
    public ResponseEntity<Object> saveCar(@RequestBody @Valid CarDto carDto){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(carService.save(carDto));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCar(@PathVariable UUID id, @RequestBody @Valid CarDto carDto){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(carService.update(id, carDto));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Object>> getAllCar(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(Collections.singletonList(carService.findAll()));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonList(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneCar(@PathVariable UUID id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(carService.findById(id));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteAllCar(){
        try{
            carService.deleteAll();
            return ResponseEntity.status(HttpStatus.OK).body("Cars deleted successfully.");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOneCar(@PathVariable UUID id){
        try{
            carService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Car deleted successfully.");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
