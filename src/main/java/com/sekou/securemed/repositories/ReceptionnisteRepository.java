package com.sekou.securemed.repositories;

import com.sekou.securemed.entities.Caissier;
import com.sekou.securemed.entities.Receptionniste;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceptionnisteRepository extends JpaRepository<Receptionniste, Long> {

    Receptionniste findReceptionnisteByTel(String tel);
    Receptionniste findReceptionnisteByCode(String code);
}
