package com.greenblat.etuep.controller;

import com.greenblat.etuep.dto.RegistrationDto;
import com.greenblat.etuep.exception.UserNotRegisterException;
import com.greenblat.etuep.handler.FieldErrorResponse;
import com.greenblat.etuep.service.AuthService;
import com.greenblat.etuep.validation.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserValidator userValidator;

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("user") RegistrationDto registrationDto) {
        return "auth/registration";
    }

    @PostMapping
    public String registrationPerform(@ModelAttribute("user") @Validated RegistrationDto registrationDto,
                                      BindingResult bindingResult) {
        validationUser(registrationDto, bindingResult);

        authService.registrationUser(registrationDto);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    private void validationUser(RegistrationDto user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            FieldErrorResponse error = new FieldErrorResponse();

            for (FieldError field : bindingResult.getFieldErrors()) {
                error.add(field.getField(), field.getDefaultMessage());
            }

            throw new UserNotRegisterException(error.toString());
        }

    }
}
