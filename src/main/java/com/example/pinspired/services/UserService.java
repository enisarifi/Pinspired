package com.example.pinspired.services;


import com.example.pinspired.dtos.RegisterUserRequestDto;
import com.example.pinspired.dtos.UserDto;
import com.example.pinspired.entities.UserEntity;
import com.example.pinspired.mappers.UserMapperImpl;
import lombok.RequiredArgsConstructor;

import java.util.stream.Collectors;
public interface UserService {

    UserDto login(String email, String password);
    boolean register(RegisterUserRequestDto registerUserRequestDto);
    void savePostForUser(Long userId, Long postId);
    void unsavePostForUser(Long userId, Long postId);
    UserDto convertToDto(UserEntity user);
    UserDto getUserById(Long userId);
}
