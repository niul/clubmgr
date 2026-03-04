package com.niulbird.clubmgr.bfc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.security.access.AccessDeniedException;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Centralized exception handler for the clubmgr-bfc application.
 * Provides consistent error views and logging for various types of exceptions.
 */
@ControllerAdvice
public class GlobalExceptionHandler extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles 404 (Not Found) errors.
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView handleNotFound(HttpServletRequest request, Exception e) {
        log.warn("404 Not Found: {} - {}", request.getRequestURL(), e.getMessage());
        return setView("error/404", "Page Not Found", request);
    }

    /**
     * Handles 403 (Access Denied) errors.
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView handleAccessDenied(HttpServletRequest request, Exception e) {
        log.warn("403 Forbidden: {} - {}", request.getRequestURL(), e.getMessage());
        return setView("error/403", "Access Denied", request);
    }

    /**
     * Generic handler for all other exceptions.
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView handleGenericException(HttpServletRequest request, Exception e) {
        log.error("Unhandled Exception at {}: {}", request.getRequestURL(), e.getMessage(), e);
        ModelAndView mav = setView("error/generic", "Internal Server Error", request);
        mav.addObject("exception", e.getMessage());
        return mav;
    }
}
