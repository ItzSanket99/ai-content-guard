package com.sanket.aicontentguard.auth.service;

import com.sanket.aicontentguard.auth.dto.LoginRequestDTO;
import com.sanket.aicontentguard.auth.dto.LoginResponseDTO;
import com.sanket.aicontentguard.auth.dto.RegisterRequestDTO;
import com.sanket.aicontentguard.audit.entity.AuditAction;
import com.sanket.aicontentguard.auth.entity.Role;
import com.sanket.aicontentguard.auth.entity.User;
import com.sanket.aicontentguard.auth.exception.InvalidCredentialsException;
import com.sanket.aicontentguard.auth.exception.ResourceAlreadyExistsException;
import com.sanket.aicontentguard.auth.repository.UserRepository;
import com.sanket.aicontentguard.auth.security.JwtService;
import com.sanket.aicontentguard.audit.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuditLogService auditLogService;

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

        auditLogService.log(
                AuditAction.USER_REGISTERED,
                user.getEmail(),
                "User registration successful"
        );

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
                        user
                );

        auditLogService.log(
                AuditAction.USER_LOGIN,
                user.getEmail(),
                "User logged in"
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