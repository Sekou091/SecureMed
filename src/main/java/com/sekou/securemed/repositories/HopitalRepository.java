package com.sekou.securemed.repositories;

import com.sekou.securemed.entities.Hopital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HopitalRepository extends JpaRepository<Hopital, Long> {
    Hopital findHopitalByNomHopital(String nomHopital);
    Hopital findHopitalByEmail(String email);

}
