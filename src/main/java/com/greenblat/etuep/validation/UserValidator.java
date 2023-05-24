package com.greenblat.etuep.validation;

import com.greenblat.etuep.dto.RegistrationDto;
import com.greenblat.etuep.model.User;
import com.greenblat.etuep.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserValidator implements Validator {

    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        RegistrationDto dto = (RegistrationDto) o;

        // Error 1
        Optional<User> optionalUser = userRepository.findByUsername(dto.getUsername());
        if (optionalUser.isPresent()) {
            errors.rejectValue("username", "", "Username should be unique");
        }
    }
}
