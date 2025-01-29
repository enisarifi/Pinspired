package com.example.pinspired.mappers;

import com.example.pinspired.dtos.RegisterUserRequestDto;
import com.example.pinspired.dtos.UserDto;
import com.example.pinspired.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
//import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper extends SimpleMapper<UserEntity, UserDto> {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserEntity userRequestDtoToUser(RegisterUserRequestDto registerUserRequestDto);
//    UserEntity userRequestDtoToUser(RegisterUserRequestDto registerUserRequestDto);

}
