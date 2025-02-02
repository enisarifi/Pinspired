package com.example.pinspired.controllers;

import com.example.pinspired.dtos.PostDto;
import com.example.pinspired.dtos.UserDto;
import com.example.pinspired.entities.PostEntity;
import com.example.pinspired.entities.UserEntity;
import com.example.pinspired.exceptions.UserNotFoundException;
import com.example.pinspired.mappers.UserMapper;
import com.example.pinspired.repositories.UserRepository;
import com.example.pinspired.services.PostService;
import com.example.pinspired.services.UserService;
import com.example.pinspired.services.impls.PostServiceImpl;
import com.example.pinspired.services.impls.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jdk.jfr.Registered;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ProfileController {
    private final PostService postService;
    private final UserRepository userRepository;
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping({"/profile", "/profile/{userId}"})
    public String profile(
            @PathVariable(required = false) Long userId,
            @RequestParam(defaultValue = "created") String tab,
            Model model,
            HttpServletRequest request) {

        UserDto currentUser = (UserDto) request.getSession().getAttribute("user");
        UserEntity profileUser;

        if (userId == null) { // I bjen je n tanen
            if (currentUser == null) return "redirect:/login";
            profileUser = userRepository.findById(currentUser.getId())
                    .orElseThrow(UserNotFoundException::new);
        } else { // I bjen t dikujt tjeter
            profileUser = userRepository.findById(userId)
                    .orElseThrow(UserNotFoundException::new);
        }

        List<PostDto> createdPosts = postService.getPostsByUserId(profileUser.getId());
        List<PostDto> savedPosts = postService.getPostsByIds(
                profileUser.getSavedPosts().stream()
                        .map(PostEntity::getId)
                        .collect(Collectors.toList())
        );

        model.addAttribute("user", userMapper.toDto(profileUser));
        model.addAttribute("createdPosts", createdPosts);
        model.addAttribute("savedPosts", savedPosts);
        model.addAttribute("selectedTab", tab);
        model.addAttribute("isOwnProfile", currentUser != null &&
                currentUser.getId() == profileUser.getId());

        return "profile";
    }

    @GetMapping("/create-post")
    public String createPost(Model model, HttpServletRequest request) {
        UserDto user = (UserDto) request.getSession().getAttribute("user");
        if (user == null) return "redirect:/login";

        model.addAttribute("user", user);
        model.addAttribute("postDto", new PostDto());
        model.addAttribute("pageName", "create-post");
        return "posts/index";
    }
}