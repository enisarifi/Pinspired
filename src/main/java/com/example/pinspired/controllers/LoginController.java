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
@RequestMapping
public class LoginController {

    private final UserService userService;

    @GetMapping("/login")
    public String login(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("user") != null) {
            return "redirect:/posts";
        }

        System.out.println("No user in session, showing login form.");
        model.addAttribute("imageUrl", "/images/icons/backgroundForLoginAndSignup.jpg");
        model.addAttribute("loginRequestDto", new LoginRequestDto());
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginRequestDto loginRequestDto,
                        BindingResult bindingResult,
                        HttpServletRequest request,
                        HttpServletResponse response) {

        if (bindingResult.hasErrors()) {
            System.out.println("Validation errors found, returning to login page.");
            return "auth/login";
        }

        try {
            var userDto = userService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());

            System.out.println("User successfully logged in, storing user in session.");

            HttpSession session = request.getSession();
            session.setAttribute("user", userDto);

            System.out.println("Session user attribute: " + session.getAttribute("user"));

            Cookie cookie = new Cookie("userId", String.valueOf(userDto.getId()));
            cookie.setMaxAge(loginRequestDto.isRememberMe() ? 60 * 60 * 24 * 30 : 60 * 60);
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setPath("/");
            response.addCookie(cookie);

            System.out.println("Cookie set: userId=" + cookie.getValue());

            return "redirect:/posts";
        } catch (UserNotFoundException e) {
            System.out.println("User not found, showing error for email.");
            bindingResult.rejectValue("email", "error.loginRequestDto", "User with this email does not exist.");
        } catch (WrongPasswordException e) {
            System.out.println("Wrong password, showing error for password.");
            bindingResult.rejectValue("password", "error.loginRequestDto", "Incorrect password.");
        }

        return "auth/login";
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

        System.out.println("-------------------------------------------------------------------------------");
        System.out.println("Received Surname: " + registerUserRequestDto.getSurname());

        if (bindingResult.hasErrors()) {
            return "auth/register";
        }

        if (!registerUserRequestDto.getPassword().equals(registerUserRequestDto.getConfirmPassword())) {
            bindingResult.rejectValue("password", "error.registerUserRequestDto", "Passwords do not match");
            bindingResult.rejectValue("confirmPassword", "error.registerUserRequestDto", "Passwords do not match");
            return "auth/register";
        }

        try {
            userService.register(registerUserRequestDto);
        } catch (UsernameExistException e) {
            bindingResult.rejectValue("username", "error.registerUserRequestDto", "Username already exists");
            return "auth/register";
        } catch (EmailExistException e) {
            bindingResult.rejectValue("email", "error.registerUserRequestDto", "Email already exists");
            return "auth/register";
        }






        return "redirect:/login";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // Remove user from session
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            System.out.println("Session invalidated during logout.");
        }

        // Clear the cookie
        Cookie cookie = new Cookie("userId", "");
        cookie.setMaxAge(0);  // Expire the cookie
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        return "redirect:/login";  // Redirect to login page after logout
    }
}
