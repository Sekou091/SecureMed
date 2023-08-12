package com.sekou.securemed.repositories;

import com.sekou.securemed.entities.Caissier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaissierRepository extends JpaRepository<Caissier, Long> {

    Caissier findCaissierByTel(String tel);
    Caissier findCaissierByCode(String code);

}
