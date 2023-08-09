package com.sekou.securemed.services;

import com.sekou.securemed.entities.TokenRevocation;
import com.sekou.securemed.repositories.TokenRevocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Transactional
public class TokenRevocationService {

    private final TokenRevocationRepository tokenRevocationRepository;

    @Autowired
    public TokenRevocationService(TokenRevocationRepository tokenRevocationRepository) {
        this.tokenRevocationRepository = tokenRevocationRepository;
    }

    public void revokeToken(String token) {
        TokenRevocation tokenRevocation = new TokenRevocation();
        tokenRevocation.setToken(token);
        tokenRevocationRepository.save(tokenRevocation);
    }

    public boolean isTokenRevoked(String token) {
        return tokenRevocationRepository.existsByToken(token);
    }
}

