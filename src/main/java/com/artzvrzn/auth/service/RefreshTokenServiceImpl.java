package com.artzvrzn.auth.service;

import com.artzvrzn.auth.dao.nosql.RefreshTokenRepository;
import com.artzvrzn.auth.domain.RefreshToken;
import com.artzvrzn.auth.service.api.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
  private final RefreshTokenRepository repository;

  @Override
  public RefreshToken get(String id) {
    return repository.findById(id).orElse(null);
  }

  @Override
  public void save(String id, String token) {
    repository.save(new RefreshToken(id, token));
  }

  @Override
  public void update(String id, String token) {
    RefreshToken entity = repository.findById(id).orElse(null);
    if (entity != null) {
      entity.setToken(token);
      repository.save(entity);
    }
  }

  @Override
  public boolean isValid(String id, String token) {
    RefreshToken refreshToken = repository.findById(id).orElse(null);
    return refreshToken != null && token.equals(refreshToken.getToken());
  }
}
