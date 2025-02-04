package com.example.pinspired.controllers;

import com.example.pinspired.dtos.PostDto;
import com.example.pinspired.dtos.UserDto;
import com.example.pinspired.entities.PostEntity;
import com.example.pinspired.entities.UserEntity;
import com.example.pinspired.helpers.FileHelper;
import com.example.pinspired.repositories.UserRepository;
import com.example.pinspired.services.PostService;
import com.example.pinspired.services.UserService;
import com.example.pinspired.services.impls.PostServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final PostServiceImpl postServiceImpl;

    @GetMapping
    public String explorePosts(HttpServletRequest request, Model model) {
        var user = (UserDto) request.getSession().getAttribute("user");
        var users = userRepository.findAll();
        var posts = postService.findAll();
        for (PostDto post : posts) {
            var postUser = userRepository.findById(post.getUserId()).orElse(null);
            if (postUser != null) {
                post.setUsername(postUser.getUsername());
                post.setProfilePicture(postUser.getProfilePicture());
            }
        }



        model.addAttribute("user", user);
        model.addAttribute("isAuthenticated", user != null);
        model.addAttribute("posts", posts);
        model.addAttribute("pageName", "explore");

        return "posts/index";
    }






    @PostMapping
    public String handleCreatePost(
            Model model,
            @ModelAttribute("postDto") @Valid PostDto postDto,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam(value = "imageUrl", required = false) String imageUrl,
            HttpServletRequest request
    ) {
        UserDto user = (UserDto) request.getSession().getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        postDto.setUserId(user.getId());
        postDto.setCreatedAt(LocalDateTime.now());
        postDto.setModifiedAt(LocalDateTime.now());

        try {
            if (image != null && !image.isEmpty()) {
                String savedFileName = FileHelper.saveFile(image);
                postDto.setImageUrl(savedFileName);
            } else if (imageUrl != null && !imageUrl.trim().isEmpty()) {
                String savedFileName = FileHelper.saveFileFromUrl(imageUrl);
                postDto.setImageUrl(savedFileName);
            }
        } catch (MalformedURLException e) {
            System.err.println("Invalid image URL: " + e.getMessage());
            return "redirect:/posts/create?error=invalid_url";
        } catch (IOException e) {
            System.err.println("File upload failed: " + e.getMessage());
            return "redirect:/posts/create?error=upload_failed";
        }

        PostEntity createdPost = postService.create(postDto);

        Optional<UserEntity> userEntityOptional = userRepository.findById(user.getId());
        userEntityOptional.ifPresent(userEntity -> {
            if (userEntity.getPostEntities() == null) {
                userEntity.setPostEntities(new ArrayList<>());
            }
            userEntity.getPostEntities().add(createdPost);
            userRepository.save(userEntity);
        });

        model.addAttribute("user", user);

        return "redirect:/posts";
    }

    @GetMapping("/saved")
    public String savedPosts(HttpServletRequest request, Model model) {
        UserDto user = (UserDto) request.getSession().getAttribute("user");
        if (user == null) return "redirect:/login";

        List<PostDto> savedPosts = postService.getPostsByIds(user.getSavedPostIds());
        model.addAttribute("posts", savedPosts);
        model.addAttribute("pageName", "saved");
        return "posts/index";
    }


    @PostMapping("/save/{postId}")
    @ResponseBody
    public ResponseEntity<?> savePost(@PathVariable Long postId, HttpServletRequest request) {
        UserDto userDto = (UserDto) request.getSession().getAttribute("user");
        if (userDto == null) return ResponseEntity.status(401).build();

        userService.savePostForUser(userDto.getId(), postId);

        UserDto updatedUser = userService.getUserById(userDto.getId()); //Cannot resolve method 'getUserById' in 'UserService'
        request.getSession().setAttribute("user", updatedUser);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/unsave/{postId}")
    @ResponseBody
    public ResponseEntity<?> unsavePost(@PathVariable Long postId, HttpServletRequest request) {
        UserDto userDto = (UserDto) request.getSession().getAttribute("user");
        if (userDto == null) return ResponseEntity.status(401).build();

        userService.unsavePostForUser(userDto.getId(), postId);

        UserDto updatedUser = userService.getUserById(userDto.getId()); // same things
        request.getSession().setAttribute("user", updatedUser);

        return ResponseEntity.ok().build();
    }






    @GetMapping("/create")
    public String createPost(Model model) {
        model.addAttribute("postDto", new PostDto());
        model.addAttribute("pageName", "create-post");
        return "posts/index";
    }




//    @PostMapping
//    public String handleCreatePost(@ModelAttribute("postDto") @Valid PostDto postDto, HttpServletRequest request) {
//        var user = (UserDto) request.getSession().getAttribute("user");
//        System.out.println("PostDto before save:::::::::::::::::::::::::::::::::: " + postDto);  // Log the PostDto
//
//        if (user == null) {
//            return "redirect:/login";
//        }
//
//        System.out.println("User from session: " + user);
//
//        long userId = user.getId();
//        postDto.setUserId(userId);
//        System.out.println("User ID from session: " + user.getId());
//
//
//        // Ensure userId is positive
//        if (postDto.getUserId() <= 0) {
//            postDto.setUserId(1L);
//        }
//        System.out.println("PostDto UserId: " + postDto.getUserId());
//
//
//        postDto.setCreatedAt(LocalDateTime.now());
//        postDto.setModifiedAt(LocalDateTime.now());
//
//        postService.create(postDto);
//        return "redirect:/posts";
//    }



}
