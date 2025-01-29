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
    public String login(HttpServletRequest request, Model model) {
        // Check if the user is already logged in
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("user") != null) {
            // Log the session status for debugging
            System.out.println("User is already logged in, redirecting to explore...");
            return "redirect:/posts";  // Redirect to explore if logged in
        }

        System.out.println("No user in session, showing login form.");
        model.addAttribute("loginRequestDto", new LoginRequestDto());
        return "auth/login";  // Show login page if no user in session
    }

    @PostMapping
    public String login(@Valid @ModelAttribute LoginRequestDto loginRequestDto,
                        BindingResult bindingResult,
                        HttpServletRequest request,
                        HttpServletResponse response) {

        // Check if there are validation errors
        if (bindingResult.hasErrors()) {
            System.out.println("Validation errors found, returning to login page.");
            return "auth/login"; // Redisplay login with errors
        }

        try {
            var userDto = userService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());

            // Log successful login
            System.out.println("User successfully logged in, storing user in session.");

            // Save user in session
            HttpSession session = request.getSession();
            session.setAttribute("user", userDto);

            // Log the session attributes
            System.out.println("Session user attribute: " + session.getAttribute("user"));

            // Set cookie for 'Remember Me' functionality
            Cookie cookie = new Cookie("userId", String.valueOf(userDto.getId()));
            cookie.setMaxAge(loginRequestDto.isRememberMe() ? 60 * 60 * 24 * 30 : 60 * 60);
            response.addCookie(cookie);

            // Log the cookie set status
            System.out.println("Cookie set: userId=" + cookie.getValue());

            return "redirect:/posts";  // Redirect to explore after successful login
        } catch (UserNotFoundException e) {
            System.out.println("User not found, showing error for email.");
            bindingResult.rejectValue("email", "error.loginRequestDto", "User with this email does not exist.");
        } catch (WrongPasswordException e) {
            System.out.println("Wrong password, showing error for password.");
            bindingResult.rejectValue("password", "error.loginRequestDto", "Incorrect password.");
        }

        // If login failed, return to login page with error messages
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
        response.addCookie(cookie);

        return "redirect:/login";  // Redirect to login page after logout
    }
}
