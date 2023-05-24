package com.greenblat.etuep.validation.impl;

import com.greenblat.etuep.repository.UserRepository;
import com.greenblat.etuep.validation.UniqueUsername;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class UsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return !userRepository.findByUsername(s).isPresent();
    }
}
