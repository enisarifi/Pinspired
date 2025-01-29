package com.example.pinspired.mappers;

import com.example.pinspired.dtos.RegisterUserRequestDto;
import com.example.pinspired.dtos.UserDto;
import com.example.pinspired.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapperImpl implements UserMapper {
//    @Override
//    public UserDto toDto(UserEntity user) {
//        UserDto userDto = new UserDto();
//        userDto.setId(String.valueOf(user.getId()));
//        userDto.setUsername(user.getUsername());
//        userDto.setEmail(user.getEmail());
//        userDto.setName(user.getName());
//        userDto.setSurname(user.getSurname());
//        userDto.setBirthdate(user.getBirthdate());
//        userDto.setActive(user.isActive());
//        return userDto;
//    }


    @Override
    public UserEntity toEntity(UserDto userDto) {
        UserEntity user = new UserEntity();
        user.setId(Long.parseLong(userDto.getId()));
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setBirthdate(userDto.getBirthdate());
        user.setActive(userDto.isActive());
        return user;
    }

    @Override
    public UserDto toDto(UserEntity userEntity) {
        UserDto user = new UserDto();
        user.setId(String.valueOf(userEntity.getId()));
        user.setUsername(userEntity.getUsername());
        user.setEmail(userEntity.getEmail());
        user.setName(userEntity.getName());
        user.setSurname(userEntity.getSurname());
        user.setBirthdate(userEntity.getBirthdate());
        user.setActive(userEntity.isActive());
        return user;
    }


    @Override
    public List<UserEntity> toEntities(List<UserDto> userDtos) {
        List<UserEntity> userEntities = List.of();
        for(UserDto userDto : userDtos) {
            userEntities.add(toEntity(userDto));
        }
        return userEntities;
    }

    @Override
    public List<UserDto> toDtos(List<UserEntity> userEntities) {
        List<UserDto> userDtos = List.of();
        for(UserEntity userEntity : userEntities) {
            userDtos.add(toDto(userEntity));
        }
        return userDtos;
    }


    @Override
    public UserEntity userRequestDtoToUser(RegisterUserRequestDto registerUserRequestDto) {
        UserEntity user = new UserEntity();
        user.setUsername(registerUserRequestDto.getUsername());
        user.setEmail(registerUserRequestDto.getEmail());
        user.setName(registerUserRequestDto.getName());
        user.setSurname(registerUserRequestDto.getSurname());
        user.setBirthdate(String.valueOf(registerUserRequestDto.getBirthdate()));
        return user;
    }


}
