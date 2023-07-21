package com.api.parkingcontrol.service;

import com.api.parkingcontrol.dtos.UserDto;
import com.api.parkingcontrol.model.User;
import com.api.parkingcontrol.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    @Transactional
    public Object save(UserDto userDto){
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        user.setRegistrationDate(LocalDateTime.now());
        user.setUpdateDate(LocalDateTime.now());
        return userRepository.save(user);
    }

    public Object update(UUID id, UserDto userDto) throws Exception {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);

        var idCurrent = userRepository.findById(id);

        if(idCurrent.isPresent()) {
            user.setId(id);
            user.setUpdateDate(LocalDateTime.now());
            user.setRegistrationDate(idCurrent.get().getRegistrationDate());
            return userRepository.save(user);
        }
        throw new Exception("User not found.");
    }

    public Object findAll() throws Exception {
        if(userRepository.findAll().isEmpty()) {
            throw new Exception("There are no registered users.");
        }
        return userRepository.findAll();
    }

    public Object findById(UUID id) throws Exception {
        if (userRepository.findById(id).isEmpty()) {
            throw new Exception("User not found.");
        }
        return userRepository.findById(id).get();
    }

    public void deleteAll() throws Exception{
        if(userRepository.findAll().isEmpty()) {
            throw new Exception("There are no registered users.");
        }
        userRepository.deleteAll();
    }

    public void deleteById(UUID id) throws Exception {
        userRepository.findById(id).orElseThrow(() -> new Exception("User not found."));
        userRepository.deleteById(id);
    }
}

