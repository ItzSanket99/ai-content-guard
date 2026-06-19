package com.sanket.aicontentguard.service;

import com.sanket.aicontentguard.dto.RegisterRequestDTO;
import com.sanket.aicontentguard.entity.Role;
import com.sanket.aicontentguard.entity.User;
import com.sanket.aicontentguard.exception.ResourceAlreadyExistsException;
import com.sanket.aicontentguard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public String registerUser(RegisterRequestDTO request) {

        if(userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResourceAlreadyExistsException(
                    "Email already registered");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword()) // encrypted later
                .role(Role.USER)
                .build();

        userRepository.save(user);

        return "User registered successfully";
    }
}