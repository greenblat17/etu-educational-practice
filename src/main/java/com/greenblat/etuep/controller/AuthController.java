package com.greenblat.etuep.controller;

import com.greenblat.etuep.dto.RegistrationDto;
import com.greenblat.etuep.service.AuthService;
import com.greenblat.etuep.validation.group.RegisterAction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.groups.Default;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("user") RegistrationDto registrationDto) {
        return "auth/registration";
    }

    @PostMapping
    public String registrationPerform(@ModelAttribute("user") @Validated({Default.class, RegisterAction.class}) RegistrationDto registrationDto,
                                      BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("user", registrationDto);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/registration";
        }

        authService.registrationUser(registrationDto);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }
}
