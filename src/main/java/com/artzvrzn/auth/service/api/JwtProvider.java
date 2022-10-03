package com.artzvrzn.auth.service.api;

import com.artzvrzn.auth.domain.User;
import io.jsonwebtoken.Claims;

public interface JwtProvider {

  String generateAccessToken(User user);

  String generateRefreshToken(User user);

  boolean validateAccessToken(String accessToken);

  boolean validateRefreshToken(String refreshToken);

  Claims getRefreshTokenClaims(String refreshToken);

  Claims getAccessTokenClaims(String accessToken);
}
