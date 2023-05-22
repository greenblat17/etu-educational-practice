package com.greenblat.etuep.mapper;

import com.greenblat.etuep.dto.RegistrationDto;
import com.greenblat.etuep.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public User mapToUser(RegistrationDto registrationDto) {
        return User.builder()
                .username(registrationDto.getUsername())
                .password(passwordEncoder.encode(registrationDto.getPassword()))
                .build();
    }

}
