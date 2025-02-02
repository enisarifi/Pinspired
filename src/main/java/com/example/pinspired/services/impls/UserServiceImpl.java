package com.example.pinspired.services.impls;

import com.example.pinspired.dtos.PostDto;
import com.example.pinspired.dtos.RegisterUserRequestDto;
import com.example.pinspired.dtos.UserDto;
import com.example.pinspired.entities.PostEntity;
import com.example.pinspired.entities.UserEntity;
import com.example.pinspired.exceptions.EmailExistException;
import com.example.pinspired.exceptions.UserNotFoundException;
import com.example.pinspired.exceptions.UsernameExistException;
import com.example.pinspired.exceptions.WrongPasswordException;
import com.example.pinspired.mappers.PostMapper;
import com.example.pinspired.mappers.UserMapper;
import com.example.pinspired.repositories.PostRepository;
import com.example.pinspired.repositories.UserRepository;
import com.example.pinspired.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
//    private final UserMapperImpl userMapperImpl;
    private final UserMapper userMapper;
    private final PostMapper postMapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDto login(String email, String password) {
        UserEntity user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            throw new UserNotFoundException();
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new WrongPasswordException();
        }

        return userMapper.toDto(user);
    }

    @Override
    public boolean register(RegisterUserRequestDto registerUserRequestDto) {
        if (userRepository.findByUsername(registerUserRequestDto.getUsername()).isPresent()) {
            throw new UsernameExistException();
        }
        if (userRepository.findByEmail(registerUserRequestDto.getEmail()).isPresent()) {
            throw new EmailExistException();
        }

        UserEntity user = userMapper.userRequestDtoToUser(registerUserRequestDto);
        user.setActive(true);

        user.setPassword(passwordEncoder.encode(registerUserRequestDto.getPassword()));

        userRepository.save(user);

        return true;
    }

    public void savePostForUser(Long userId, Long postId) {
        UserEntity user = userRepository.findById(userId).orElseThrow();
        PostEntity post = postRepository.findById(postId).orElseThrow();
        user.getSavedPosts().add(post);
        userRepository.save(user);
    }


    public void unsavePostForUser(Long userId, Long postId) {
        UserEntity user = userRepository.findById(userId).orElseThrow();
        user.setSavedPosts(
                user.getSavedPosts().stream()
                        .filter(p -> p.getId() != postId)
                        .collect(Collectors.toList())
        );
        userRepository.save(user);
    }


    @Override
    public UserDto convertToDto(UserEntity user) {
        UserDto dto = userMapper.toDto(user);
        dto.setSavedPostIds(user.getSavedPosts().stream()
                .map(PostEntity::getId)
                .collect(Collectors.toList()));
        return dto;
    }

    @Override
    public UserDto getUserById(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException());
        return userMapper.toDto(user);
    }

}










