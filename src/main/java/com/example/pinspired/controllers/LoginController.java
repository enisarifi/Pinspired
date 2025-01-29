package com.example.pinspired.controllers;

import com.example.pinspired.dtos.LoginRequestDto;
import com.example.pinspired.dtos.RegisterUserRequestDto;
import com.example.pinspired.exceptions.EmailExistException;
import com.example.pinspired.exceptions.UserNotFoundException;
import com.example.pinspired.exceptions.UsernameExistException;
import com.example.pinspired.exceptions.WrongPasswordException;
import com.example.pinspired.services.UserService;
import jakarta.servlet.http.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final UserService userService;


    @GetMapping
    public String login(Model model) {
        model.addAttribute("loginRequestDto", new LoginRequestDto());
        return "auth/login";
    }

//    @PostMapping
//    public String login(@Valid @ModelAttribute LoginRequestDto loginRequestDto,
//                        BindingResult  bindingResult,
//                        HttpServletRequest request,
//                        HttpServletResponse response,
//                        @RequestParam(value = "returnUrl", required = false) String returnUrl
//                        ) {
//
//        if (bindingResult.hasErrors()) {
//            return "auth/login";
//        }
//
//        try {
//            var userDto = userService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
//
//
//            HttpSession session = request.getSession();
//            session.setAttribute("user", userDto);
//
//            Cookie cookie = new Cookie("userId", "" + userDto.getId());
//            if (loginRequestDto.isRememberMe()) {
//                cookie.setMaxAge(60 * 60 * 24 * 30); // 30 days
//            } else {
//                cookie.setMaxAge(60 * 60); // 1 hour
//            }
//
//            response.addCookie(cookie);
//            if (returnUrl == null || returnUrl.isBlank())
//                return "redirect:/";
//            return "redirect:" + returnUrl;
//        } catch (UserNotFoundException e) {
//            bindingResult.rejectValue("email", "error.loginRequestDto",
//                    "User with this email does not exist");
//            return "auth/login";
//        } catch (WrongPasswordException e) {
//            bindingResult.rejectValue("password", "error.loginRequestDto",
//                    e.getMessage());
//            return "auth/login";
//        }
//    }


    @PostMapping
    public String login(@Valid @ModelAttribute LoginRequestDto loginRequestDto,
                        BindingResult bindingResult,
                        HttpServletRequest request,
                        HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return "auth/login"; // Redisplay login with errors
        }

        try {
            var userDto = userService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());

            // Save user in session
            HttpSession session = request.getSession();
            session.setAttribute("user", userDto);

            // Set cookie for 'Remember Me' functionality
            Cookie cookie = new Cookie("userId", String.valueOf(userDto.getId()));
            cookie.setMaxAge(loginRequestDto.isRememberMe() ? 60 * 60 * 24 * 30 : 60 * 60);
            response.addCookie(cookie);

            return "redirect:/";
        } catch (UserNotFoundException e) {
            bindingResult.rejectValue("email", "error.loginRequestDto", "User with this email does not exist.");
        } catch (WrongPasswordException e) {
            bindingResult.rejectValue("password", "error.loginRequestDto", "Incorrect password.");
        }

        return "auth/login"; // Return to login on failure
    }


    @GetMapping("/reset-password")
    public String resetPassword() {
        return "auth/reset-password";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("registerUserRequestDto", new RegisterUserRequestDto());
        model.addAttribute("maxDate", LocalDate.now().minusYears(18));
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute RegisterUserRequestDto registerUserRequestDto,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "auth/register";
        }

        if (!registerUserRequestDto.getPassword().equals(registerUserRequestDto.getConfirmPassword())) {
            bindingResult.rejectValue("password", "error.registerUserRequestDto", "Passwordet nuk perputhen");
            bindingResult.rejectValue("confirmPassword", "error.registerUserRequestDto", "Passwordet nuk perputhen");
            return "auth/register";
        }

        try {
            userService.register(registerUserRequestDto);
        } catch (UsernameExistException e) {
            bindingResult.rejectValue("username", "error.registerUserRequestDto",
                    "Ky username ekziston");
            return "auth/register";
        } catch (EmailExistException e) {
            bindingResult.rejectValue("email", "error.registerUserRequestDto",
                    "Ky email ekziston");
            return "auth/register";
        }


        return "redirect:/login";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie("userId", "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        HttpSession session = request.getSession(false);
        session.invalidate();
        return "redirect:/login";
    }


    }

