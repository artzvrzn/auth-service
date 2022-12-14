package com.artzvrzn.auth.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
  private String username;
  private String password;
  private String repeatPassword;
  private String firstName;
  private String lastName;
}
