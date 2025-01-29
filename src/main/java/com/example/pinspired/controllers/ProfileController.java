package com.example.pinspired.controllers;

import com.example.pinspired.dtos.PostDto;
import com.example.pinspired.dtos.UserDto;
import com.example.pinspired.services.PostService;
import com.example.pinspired.services.impls.PostServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jdk.jfr.Registered;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class ProfileController {
    private final PostService postService;



    @GetMapping("/create-post")
    public String createPost(Model model) {
        model.addAttribute("postDto", new PostDto());
        model.addAttribute("pageName", "create-post");
        return "posts/index";
    }

//    @PostMapping("/create-post")
//    public String createPost(@ModelAttribute("postDto") @Valid PostDto postDto, HttpServletRequest request) {
//        var user = (UserDto) request.getSession().getAttribute("user");
//
//        if (user == null) {
//            return "redirect:/login";  // Ensure only logged-in users can post
//        }
//
//        // Set user ID and timestamps
//        postDto.setUserId(Long.parseLong(user.getId()));
//        postDto.setCreatedAt(LocalDateTime.now());
//        postDto.setModifiedAt(LocalDateTime.now());
//
//        postService.create(postDto);  // Save the post
//
//        return "redirect:/posts/";  // Redirect to explore page
//    }
}
