package com.example.pinspired.security;

import com.example.pinspired.dtos.UserDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Configuration
public class SimpleSessionCookieFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //skip the filter for static resources
//        if (request.getRequestURI().startsWith("/assets") || request.getRequestURI().startsWith("/static") || request.getRequestURI().startsWith("/templates")) {

        if (request.getRequestURI().startsWith("/assets")) {
            filterChain.doFilter(request, response);
            return;
        }

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            if (request.getRequestURI().startsWith("/login") || request.getRequestURI().startsWith("/register")) {
                filterChain.doFilter(request, response);
                return;
            }
            response.sendRedirect("/login?returnUrl=" + request.getRequestURI());
            return;
        }

        if (request.getRequestURI().startsWith("/login") || request.getRequestURI().startsWith("/register")) {
            response.sendRedirect("/posts");
            return;
        }

        UserDto userDto = (UserDto)session.getAttribute("user");

//        // validimi i path-it sipas ROLE
//        if(userDto.getRole().equalsIgnoreCase("ADMIN")) {
//            AdminFilter.doFilter(request, response, filterChain);
//            return;
//        } else if(userDto.getRole().equalsIgnoreCase("CUSTOMER")) {
//            CustomerFilter.doFilter(request, response, filterChain);
//            return;
//        }

        filterChain.doFilter(request, response);
    }
}
