package com.sekou.securemed.repositories;

import com.sekou.securemed.entities.Maladie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaladieRepository extends JpaRepository<Maladie, Long> {
}
