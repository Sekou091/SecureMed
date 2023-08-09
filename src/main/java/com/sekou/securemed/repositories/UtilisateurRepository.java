package com.sekou.securemed.repositories;

import com.sekou.securemed.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    Utilisateur findUtilisateurByUsername(String username);
    Utilisateur findUtilisateurByEmail(String email);

}
