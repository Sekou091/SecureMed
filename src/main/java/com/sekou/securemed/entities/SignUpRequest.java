package com.sekou.securemed.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class SignUpRequest {

    String username;
    String email;
    String password;
    String confirmPassword;
    String telephone;
}
