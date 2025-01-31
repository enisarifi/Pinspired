package com.example.pinspired.controllers;

import com.example.pinspired.dtos.PostDto;
import com.example.pinspired.dtos.UserDto;
import com.example.pinspired.services.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public String explorePosts(HttpServletRequest request, Model model) {
        var user = (UserDto) request.getSession().getAttribute("user");

        model.addAttribute("isAuthenticated", user != null);
        model.addAttribute("posts", postService.findAll());
        model.addAttribute("pageName", "explore");

        return "posts/index";
    }

    @PostMapping
    public String handleCreatePost(@ModelAttribute("postDto") @Valid PostDto postDto, HttpServletRequest request) {
        var user = (UserDto) request.getSession().getAttribute("user");
        System.out.println("PostDto before save:::::::::::::::::::::::::::::::::: " + postDto);  // Log the PostDto

        if (user == null) {
            return "redirect:/login";
        }

        System.out.println("User from session: " + user);

        long userId = user.getId();
        postDto.setUserId(userId);
        System.out.println("User ID from session: " + user.getId());


        // Ensure userId is positive
        if (postDto.getUserId() <= 0) {
            postDto.setUserId(1L);
        }
        System.out.println("PostDto UserId: " + postDto.getUserId());


        postDto.setCreatedAt(LocalDateTime.now());
        postDto.setModifiedAt(LocalDateTime.now());

        postService.create(postDto);

        return "redirect:/posts";
    }

    @GetMapping("/create")
    public String createPost(Model model) {
        model.addAttribute("postDto", new PostDto());
        model.addAttribute("pageName", "create-post");
        return "posts/index";
    }
}
