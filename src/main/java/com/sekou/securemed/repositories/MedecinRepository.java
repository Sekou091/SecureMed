package com.sekou.securemed.repositories;

import com.sekou.securemed.entities.Medecin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedecinRepository extends JpaRepository<Medecin, Long> {

    Medecin findMedecinByEmail(String email);
    Medecin findMedecinByCode(String code);
}
