package com.example.pinspired.controllers;

import com.example.pinspired.dtos.UserDto;
import com.example.pinspired.services.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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


//    @GetMapping("/edit/{postId}")
//    public String editPost(@PathVariable long postId, HttpServletRequest request, Model model) {
//        var user = (UserDto) request.getSession().getAttribute("user");
//        if (user == null) {
//            return "redirect:/login";
//        }
//
//        var post = service.findById(postId);
//        if (post == null || post.getUserId() == user.getId()) {
//            return "error/403";  // Render an unauthorized access page
//        }
//
//        model.addAttribute("post", post);
//        return "posts/edit";
//    }



    @GetMapping("/explore")
    public String explorePosts(HttpServletRequest request, Model model) {
        var user = (UserDto) request.getSession().getAttribute("user");
        model.addAttribute("isAuthenticated", user != null);
        model.addAttribute("posts", service.findAll());
        return "posts/explore";
    }


}


// http://localhost:8080/api/v1/users/1/posts