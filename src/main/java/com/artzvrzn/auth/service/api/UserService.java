package com.artzvrzn.auth.service.api;

import com.artzvrzn.auth.domain.User;
import com.artzvrzn.auth.dto.request.UserRequest;
import java.util.UUID;

public interface UserService {

  User create(UserRequest userRequest);

  User getById(UUID id);

  User getByUsername(String username);

  User update(UUID id, UserRequest userRequest);

  void delete(UUID id);
}
