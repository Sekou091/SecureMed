package com.sekou.securemed.repositories;

import com.sekou.securemed.entities.TokenRevocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRevocationRepository extends JpaRepository<TokenRevocation, Long> {
    boolean existsByToken(String token);
}

