package com.artzvrzn.auth.service;

import com.artzvrzn.auth.exception.AuthenticationFailedException;
import com.artzvrzn.auth.service.api.RefreshTokenService;
import com.artzvrzn.auth.service.api.UserService;
import com.artzvrzn.auth.dto.request.UserRequest;
import com.artzvrzn.auth.service.api.AuthService;
import com.artzvrzn.auth.domain.User;
import com.artzvrzn.auth.dto.request.AuthRequest;
import com.artzvrzn.auth.dto.response.JwtResponse;
import com.artzvrzn.auth.service.api.JwtProvider;
import io.jsonwebtoken.Claims;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final UserService userService;
  private final PasswordEncoder passwordEncoder;
  private final JwtProvider jwtProvider;
  private final RefreshTokenService refreshTokenService;

  @Override
  public JwtResponse login(AuthRequest authRequest) {
    User user = userService.getByUsername(authRequest.getUsername());
    if (!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
      throw new AuthenticationFailedException();
    }
    String accessToken = jwtProvider.generateAccessToken(user);
    String refreshToken = jwtProvider.generateRefreshToken(user);
    refreshTokenService.save(user.getId().toString(), refreshToken);
    return new JwtResponse(accessToken, refreshToken);
  }

  @Override
  public JwtResponse signUp(UserRequest userRequest) {
    User user = userService.create(userRequest);
    String accessToken = jwtProvider.generateAccessToken(user);
    String refreshToken = jwtProvider.generateRefreshToken(user);
    refreshTokenService.save(user.getId().toString(), refreshToken);
    return new JwtResponse(accessToken, refreshToken);
  }

  @Override
  public JwtResponse getAccessToken(String refreshToken) {
    if (jwtProvider.validateRefreshToken(refreshToken)) {
      Claims claims = jwtProvider.getRefreshTokenClaims(refreshToken);
      String userId = claims.getSubject();
      if (refreshTokenService.isValid(userId, refreshToken)) {
        User user = userService.getById(UUID.fromString(userId));
        return new JwtResponse(jwtProvider.generateAccessToken(user), null);
      }
    }
    return new JwtResponse(null, null);
  }

  @Override
  public JwtResponse refresh(String refreshToken) {
    if (jwtProvider.validateRefreshToken(refreshToken)) {
      Claims claims = jwtProvider.getRefreshTokenClaims(refreshToken);
      String userId = claims.getSubject();
      if (refreshTokenService.isValid(userId, refreshToken)) {
        User user = userService.getById(UUID.fromString(userId));
        String newAccessToken = jwtProvider.generateAccessToken(user);
        String newRefreshToken = jwtProvider.generateRefreshToken(user);
        refreshTokenService.update(userId, newRefreshToken);
        return new JwtResponse(newAccessToken, refreshToken);
      }
    }
    throw new AuthenticationFailedException("Invalid token");
  }
}
