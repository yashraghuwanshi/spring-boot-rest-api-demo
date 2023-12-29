package com.example.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @GetMapping("/error")
    public String handleError(Model model) {
        model.addAttribute("httpStatus", HttpStatus.NOT_FOUND);
        return "errors/error";  // Return the Thymeleaf view name
    }
}