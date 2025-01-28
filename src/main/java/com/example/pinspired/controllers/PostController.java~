package dev.naimsulejmani.gr1facebook.controllers;

import dev.naimsulejmani.gr1facebook.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService service;

    @GetMapping
    public String postIndex(Model model) {
        model.addAttribute("posts", service.findAll());
        return "posts/index";
    }
}


// http://localhost:8080/api/v1/users/1/posts