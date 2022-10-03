package com.artzvrzn.auth.service.api;

import com.artzvrzn.auth.domain.RefreshToken;

public interface RefreshTokenService {

  RefreshToken get(String id);

  void save(String id, String token);

  void update(String id, String token);

  boolean isValid(String id, String token);
}
