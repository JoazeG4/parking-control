package com.api.parkingcontrol.service;

import com.api.parkingcontrol.dtos.UserDto;
import com.api.parkingcontrol.model.User;
import com.api.parkingcontrol.repository.UserRepository;
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
public class UserService {

    private UserRepository userRepository;

    @Transactional
    public User save(UserDto userDto){
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        user.setRegistrationDate(LocalDateTime.now());
        user.setUpdateDate(LocalDateTime.now());
        return userRepository.save(user);
    }

    public User update(UUID id, UserDto userDto) throws EntityNotFoundException {
        var idCurrent = Optional.of(userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User not found.")));

        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        user.setId(id);
        user.setUpdateDate(LocalDateTime.now());
        user.setRegistrationDate(idCurrent.get().getRegistrationDate());
        return userRepository.save(user);
    }

    public Page<User> findAll(Pageable pageable) throws EntityBadRequestException {
        if(userRepository.findAll().isEmpty()) {
            throw new EntityBadRequestException("There are no registered users.");
        }
        return userRepository.findAll(pageable);
    }

    public User findById(UUID id) throws EntityNotFoundException {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User not found."));
    }

    public void deleteAll() throws EntityBadRequestException {
        if(userRepository.findAll().isEmpty()) {
            throw new EntityBadRequestException("There are no registered users.");
        }
        userRepository.deleteAll();
    }

    @Transactional
    public void deleteById(UUID id) throws EntityNotFoundException{
        userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found."));
        userRepository.deleteById(id);
    }
}

