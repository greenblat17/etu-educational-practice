package com.greenblat.etuep.service;

import com.greenblat.etuep.dto.RegistrationDto;
import com.greenblat.etuep.mapper.UserMapper;
import com.greenblat.etuep.model.User;
import com.greenblat.etuep.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public void registrationUser(RegistrationDto registrationDto) {
        User user = userMapper.mapToUser(registrationDto);
        userRepository.save(user);
    }
}
