package com.example.pinspired.services.impls;

import com.example.pinspired.dtos.RegisterUserRequestDto;
import com.example.pinspired.dtos.UserDto;
import com.example.pinspired.entities.UserEntity;
import com.example.pinspired.exceptions.EmailExistException;
import com.example.pinspired.exceptions.UserNotFoundException;
import com.example.pinspired.exceptions.UsernameExistException;
import com.example.pinspired.exceptions.WrongPasswordException;
import com.example.pinspired.mappers.UserMapperImpl;
import com.example.pinspired.repositories.UserRepository;
import com.example.pinspired.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserMapperImpl userMapperImpl;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repository, UserMapperImpl userMapperImpl, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.userMapperImpl = userMapperImpl;
        this.passwordEncoder = passwordEncoder;

//        if (repository.count() == 0) {
//            User user = new User();
//            user.setEmail("naim.sulejmani@gmail.com");
//            user.setActive(true);
//            user.setName("Naim");
//            user.setSurname("Sulejmani");
//            user.setBirthdate(LocalDate.parse("1986-12-16"));
//            user.setRole("ROLE_ADMIN");
//            user.setPhone("049111111");
//            user.setPassword("Admin123");
//            user.setUsername("naimsulejmani");
//
//            repository.save(user);
//
//        }
    }

    @Override
    public UserDto login(String email, String password) {
        UserEntity user = repository.findByEmail(email).orElse(null);

        if (user == null) {
            throw new UserNotFoundException();
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new WrongPasswordException();
        }

        return userMapperImpl.toDto(user);
    }

    @Override
    public boolean register(RegisterUserRequestDto registerUserRequestDto) {
        if (repository.findByUsername(registerUserRequestDto.getUsername()).isPresent()) {
            throw new UsernameExistException();
        }
        if (repository.findByEmail(registerUserRequestDto.getEmail()).isPresent()) {
            throw new EmailExistException();
        }

        UserEntity user = userMapperImpl.userRequestDtoToUser(registerUserRequestDto);
        user.setActive(true);

        user.setPassword(passwordEncoder.encode(registerUserRequestDto.getPassword()));

        repository.save(user);

        return true;
    }
}










