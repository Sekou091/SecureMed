package com.sekou.securemed.ressource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;


@Configuration
public class TwilioRouterConfig {
    @Autowired
    private TwilioOTPHandler handler;

    @Bean
    public RouterFunction<ServerResponse> handleSMS() {
        return route()
                .POST("router/sendOTP", handler::sendOTP)
                .POST("/router/validateOTP", handler::validateOTP)
                .build();
    }

}
