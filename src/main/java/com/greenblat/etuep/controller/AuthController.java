package com.greenblat.etuep.controller;

import com.greenblat.etuep.dto.RegistrationDto;
import com.greenblat.etuep.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("user") RegistrationDto registrationDto) {
        return "auth/registration";
    }

    @PostMapping
    public String registrationPerform(@ModelAttribute("user") RegistrationDto registrationDto) {
        authService.registrationUser(registrationDto);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }
}
