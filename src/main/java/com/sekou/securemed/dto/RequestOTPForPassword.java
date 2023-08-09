package com.sekou.securemed.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class RequestOTPForPassword {

    private String phoneNumber; //destination
    private String username;
    private String otp;
}
