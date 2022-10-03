package com.artzvrzn.auth.service;

import com.artzvrzn.auth.domain.Role;
import com.artzvrzn.auth.domain.User;
import com.artzvrzn.auth.dto.request.UserRequest;
import com.artzvrzn.auth.service.api.UserService;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private ModelMapper modelMapper;
  //temporary in-memory storage instead of sql
  private final Map<UUID, User> users = new HashMap<>();

  @Override
  public User create(UserRequest userRequest) {
    User user = modelMapper.map(userRequest, User.class);
    user.setId(UUID.randomUUID());
    user.setRoles(Collections.singleton(Role.ADMIN));
    users.put(user.getId(), user);
    return user;
  }

  @Override
  public User getById(UUID id) {
    return users.get(id);
  }

  @Override
  public User getByUsername(String username) {
    for (Map.Entry<UUID, User> entry: users.entrySet()) {
      if (entry.getValue() != null && entry.getValue().getUsername().equals(username)) {
        return entry.getValue();
      }
    }
    return null;
  }

  @Override
  public User update(UUID id, UserRequest userRequest) {
    return users.get(id);
  }

  @Override
  public void delete(UUID id) {
    users.remove(id);
  }
}
