package com.api.parkingcontrol.controller;

import com.api.parkingcontrol.dtos.UserDto;
import com.api.parkingcontrol.model.User;
import com.api.parkingcontrol.service.UserService;
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
@RequestMapping("/users")
public class UserController {

    final UserService userService;

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody @Valid UserDto userDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody @Valid UserDto userDto){
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(id, userDto));
    }

    @GetMapping
    public ResponseEntity<Page<User>> getAllUser(@PageableDefault(page = 0,
            size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getOneUser(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id));

    }

    @DeleteMapping
    public ResponseEntity<Object> deleteAllUser(){
        userService.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).body("Users deleted successfully.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOneUser(@PathVariable UUID id){
        userService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully.");
    }
}
