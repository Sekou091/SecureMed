package com.sekou.securemed.dto;

import com.sekou.securemed.entities.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class AuthRequest {

    private String username;
    private String password;
    private List<String> roles;
}
