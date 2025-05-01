package com.niulbird.clubmgr.bfc.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         org.springframework.security.core.AuthenticationException authException)
            throws IOException, ServletException {

        // Handle CSRF errors specifically
        if (authException.getCause() instanceof InvalidCsrfTokenException) {
            response.sendRedirect("/error/csrf");
        } else {
            response.sendRedirect("/login.html?error");
        }
    }
}
