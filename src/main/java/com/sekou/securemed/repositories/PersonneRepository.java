package com.sekou.securemed.repositories;

import com.sekou.securemed.entities.Personne;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonneRepository extends JpaRepository<Personne, Long> {

}
