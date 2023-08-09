package com.sekou.securemed.controllers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class RegistrationRequest {

    private String username;
    private String email;
    private String password;
    private String confirmPassword;
}
