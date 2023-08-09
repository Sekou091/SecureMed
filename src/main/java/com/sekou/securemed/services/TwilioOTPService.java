package com.sekou.securemed.services;

import com.sekou.securemed.config.TwilioConfig;
import com.sekou.securemed.dto.OtpStatus;
import com.sekou.securemed.dto.RequestOTPForPassword;
import com.sekou.securemed.dto.ResponseOTPForPassword;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class TwilioOTPService {
@Autowired
private TwilioConfig twilioConfig;
Map<String, String> otpMap = new HashMap<>();
    public Mono<ResponseOTPForPassword> sendOTPForPasswordReset(RequestOTPForPassword requestOTPForPassword){
    ResponseOTPForPassword responseOTPForPassword = null;
        try{
            PhoneNumber to = new PhoneNumber(requestOTPForPassword.getPhoneNumber());
            PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber());
            String otp = generateOTP();
            String otpMessage = "Cher utilisateur, Votre code OTP est ##" + otp +"##. Utilisez ce code pour compléter votre transaction";
            Message message = Message
                        .creator(to, from,
                                otpMessage)
                    .create();
            otpMap.put(requestOTPForPassword.getUsername(), otp);
            responseOTPForPassword = new ResponseOTPForPassword(OtpStatus.DELIVERED, otpMessage);
        }catch (Exception e){

            responseOTPForPassword = new ResponseOTPForPassword(OtpStatus.FAILED, e.getMessage());
        }

        return  Mono.just(responseOTPForPassword);
    }

    public Mono<String> validateOTP (String userInputOtp, String username){
        if(userInputOtp.equals(otpMap.get(username))){
            otpMap.remove(username, userInputOtp);
            return Mono.just("OTP valide, merci de procéder à votre transaction");
        }else {
            return Mono.error(new IllegalArgumentException("OTP invalide, merci de réessayer"));
        }
    }

    public String generateOTP(){
        return  new DecimalFormat("000000")
                .format( new Random().nextInt(999999));
    }

}
