package com.example.taskmanager.exception;

import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNotFound(NoSuchElementException ex, RedirectAttributes redirectAttributes) {
        log.warn("Requested resource not found: {}", ex.getMessage());
        redirectAttributes.addFlashAttribute("errorMessage", "That item couldn't be found.");
        return "redirect:/";
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public String handleUserNotFound(UsernameNotFoundException ex, RedirectAttributes redirectAttributes) {
        log.error("Authenticated principal has no matching user record: {}", ex.getMessage());
        SecurityContextHolder.clearContext();
        redirectAttributes.addFlashAttribute("errorMessage", "Your session is no longer valid. Please log in again.");
        return "redirect:/";
    }

    @ExceptionHandler(Exception.class)
    public String handleUnexpected(Exception ex, RedirectAttributes redirectAttributes) {
        log.error("Unhandled exception", ex);
        redirectAttributes.addFlashAttribute("errorMessage", "Something went wrong. Please try again.");
        return "redirect:/";
    }
}
