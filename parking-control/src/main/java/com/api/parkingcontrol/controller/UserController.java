package com.api.parkingcontrol.controller;

import com.api.parkingcontrol.dtos.CarDto;
import com.api.parkingcontrol.dtos.ParkingSpotDto;
import com.api.parkingcontrol.dtos.UserDto;
import com.api.parkingcontrol.model.ParkingSpot;
import com.api.parkingcontrol.model.User;
import com.api.parkingcontrol.repository.UserRepository;
import com.api.parkingcontrol.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    final UserService userService;

    @PostMapping
    public ResponseEntity<Object> saveUser(@RequestBody @Valid UserDto userDto) {
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userDto));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable UUID id, @RequestBody @Valid UserDto userDto){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(userService.update(id, userDto));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Object>> getAllUser(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(Collections.singletonList(userService.findAll()));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonList(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneUser(@PathVariable UUID id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteAllUser(){
        try{
            userService.deleteAll();
            return ResponseEntity.status(HttpStatus.OK).body("Users deleted successfully.");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOneUser(@PathVariable UUID id){
        try{
            userService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully.");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
