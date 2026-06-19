package com.sanket.aicontentguard.service;

import com.sanket.aicontentguard.dto.RegisterRequestDTO;

public interface UserService {

    String registerUser(RegisterRequestDTO request);
}