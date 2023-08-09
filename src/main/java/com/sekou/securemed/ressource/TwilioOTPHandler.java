package com.sekou.securemed.ressource;

import com.sekou.securemed.dto.RequestOTPForPassword;
import com.sekou.securemed.services.TwilioOTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class TwilioOTPHandler {
    @Autowired
    private TwilioOTPService twilioOTPService;
    public Mono<ServerResponse>  sendOTP(ServerRequest serverRequest){
        return serverRequest.bodyToMono(RequestOTPForPassword.class)
                .flatMap(dto->twilioOTPService.sendOTPForPasswordReset(dto))
                .flatMap(dto->ServerResponse.status(HttpStatus.OK)
                .body(BodyInserters.fromValue(dto)));
    }

    public Mono<ServerResponse>  validateOTP(ServerRequest serverRequest){
        return serverRequest.bodyToMono(RequestOTPForPassword.class)
                .flatMap(dto->twilioOTPService.validateOTP(dto.getOtp(), dto.getUsername()))
                .flatMap(dto->ServerResponse.status(HttpStatus.OK)
                        .bodyValue(dto));
    }
}
