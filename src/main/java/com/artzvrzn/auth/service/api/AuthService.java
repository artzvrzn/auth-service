package com.artzvrzn.auth.service.api;

import com.artzvrzn.auth.dto.request.AuthRequest;
import com.artzvrzn.auth.dto.request.UserRequest;
import com.artzvrzn.auth.dto.response.JwtResponse;

public interface AuthService {

  JwtResponse login(AuthRequest authRequest);

  JwtResponse signUp(UserRequest userRequest);

  JwtResponse getAccessToken(String refreshToken);

  JwtResponse refresh(String refreshToken);
}
