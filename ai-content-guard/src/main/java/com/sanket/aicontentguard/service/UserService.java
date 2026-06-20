package com.sanket.aicontentguard.service;

import com.sanket.aicontentguard.dto.LoginRequestDTO;
import com.sanket.aicontentguard.dto.LoginResponseDTO;
import com.sanket.aicontentguard.dto.RegisterRequestDTO;

public interface UserService {

    String registerUser(RegisterRequestDTO request);

    LoginResponseDTO loginUser(LoginRequestDTO request);
}