package com.sekou.securemed.repositories;

import com.sekou.securemed.entities.AnalyseMedicale;
import com.sekou.securemed.entities.AssuranceMedicale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalyseMedicaleRepository extends JpaRepository<AnalyseMedicale, Long> {

}
