package com.api.parkingcontrol.controller;

import com.api.parkingcontrol.dtos.ParkingSpotDto;
import com.api.parkingcontrol.model.ParkingSpot;
import com.api.parkingcontrol.service.ParkingSpotService;
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
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/parking-spots")
public class ParkingSpotController {

    private ParkingSpotService parkingSpotService;


    @PostMapping
    public ResponseEntity<ParkingSpot> saveParkingSpot(@RequestBody @Valid ParkingSpotDto parkingSpotDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotService.save(parkingSpotDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateParkingSpot(@PathVariable UUID id, @RequestBody @Valid ParkingSpotDto parkingSpotDto) {
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.update(id, parkingSpotDto));
    }

    @GetMapping
    public ResponseEntity<Page<ParkingSpot>> getAllParkingSpot(@PageableDefault(page = 0,
            size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingSpot> getOneParkingSpot(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.findById(id));

    }

    @DeleteMapping
    public ResponseEntity<Object> deleteAllParkingSpot() {
        parkingSpotService.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).body("Vacancy deleted successfully");

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOneParkingSpot(@PathVariable UUID id) {
        parkingSpotService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Vacancy deleted successfully");
    }
}
