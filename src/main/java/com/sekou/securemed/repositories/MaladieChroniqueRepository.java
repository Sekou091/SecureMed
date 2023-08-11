package com.sekou.securemed.repositories;

import com.sekou.securemed.entities.MaladieChronique;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaladieChroniqueRepository extends JpaRepository<MaladieChronique, Long> {
}
