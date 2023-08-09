package com.sekou.securemed.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class ResponseOTPForPassword {

   private OtpStatus otpStatus;
   private String message;
}
