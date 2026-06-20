package com.sanket.aicontentguard.service;

import com.sanket.aicontentguard.dto.LoginRequestDTO;
import com.sanket.aicontentguard.dto.LoginResponseDTO;
import com.sanket.aicontentguard.dto.RegisterRequestDTO;
import com.sanket.aicontentguard.entity.Role;
import com.sanket.aicontentguard.entity.User;
import com.sanket.aicontentguard.exception.InvalidCredentialsException;
import com.sanket.aicontentguard.exception.ResourceAlreadyExistsException;
import com.sanket.aicontentguard.repository.UserRepository;
import com.sanket.aicontentguard.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    @Override
    public String registerUser(RegisterRequestDTO request) {

        if(userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResourceAlreadyExistsException(
                    "Email already registered");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())) // encrypted later
                .role(Role.USER)
                .build();

        userRepository.save(user);

        return "User registered successfully";
    }

    @Override
    public LoginResponseDTO loginUser(LoginRequestDTO request) {

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new InvalidCredentialsException(
                                "Invalid email or password"
                        ));

        boolean matches = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        );

        if (!matches) {
            throw new InvalidCredentialsException(
                    "Invalid email or password"
            );
        }

        String token =
                jwtService.generateToken(
                        user.getEmail()
                );

        return LoginResponseDTO.builder()
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .message("Login successful")
                .token(token)
                .build();
    }
}