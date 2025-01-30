package com.example.pinspired.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//@Configuration
public class SimpleCookieFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        if (request.getRequestURI().startsWith("/assets")) {
            filterChain.doFilter(request, response);
            return;
        }

        System.out.println(request.getMethod() + "->" + request.getRequestURI());


        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            if (request.getRequestURI().startsWith("/login") || request.getRequestURI().startsWith("/register")) {
                filterChain.doFilter(request, response);
                return;
            }
            var returnUrl = request.getRequestURI();
            response.sendRedirect("/login?returnUrl=" + returnUrl);
            return;
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("userId")) {
                if (request.getRequestURI().startsWith("/login") || request.getRequestURI().startsWith("/register")) {
                    response.sendRedirect("/");
                    return;
                }

                filterChain.doFilter(request, response);
                return;
            }
        }

        // shko te login nese sje kan tu shku te logini dhe register
        // mundemi m ei than shko ne nje faqe UNATHORIZED

        if (request.getRequestURI().startsWith("/login") || request.getRequestURI().startsWith("/register")) {
            filterChain.doFilter(request, response);
            return;
        }

        response.sendRedirect("/login");


        //TODO!: Need to check if the user is logged in or not
        // use session instead of cookies or combined with cookies
    }
}







