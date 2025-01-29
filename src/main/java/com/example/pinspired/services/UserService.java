package com.example.pinspired.services;


import com.example.pinspired.dtos.RegisterUserRequestDto;
import com.example.pinspired.dtos.UserDto;

public interface UserService {
    UserDto login(String email, String password);
    boolean register(RegisterUserRequestDto registerUserRequestDto);
}
