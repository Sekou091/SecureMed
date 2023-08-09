package com.sekou.securemed.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ResetPasswordRequest {

    private String username;
    private String newPassword;
    private String confirmNewPassword;
    private String otp;
}
