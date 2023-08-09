package com.sekou.securemed.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class UserRoleRequest {

    private String username;
    private String roleName;
}
