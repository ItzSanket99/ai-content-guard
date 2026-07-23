package com.sanket.aicontentguard.auth.service;

import com.sanket.aicontentguard.auth.dto.LoginRequestDTO;
import com.sanket.aicontentguard.auth.dto.LoginResponseDTO;
import com.sanket.aicontentguard.auth.dto.RegisterRequestDTO;

public interface UserService {

    String registerUser(RegisterRequestDTO request);

    LoginResponseDTO loginUser(LoginRequestDTO request);
}