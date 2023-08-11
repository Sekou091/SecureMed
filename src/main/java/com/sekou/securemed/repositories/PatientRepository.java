package com.sekou.securemed.repositories;

import com.sekou.securemed.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    Patient findPatientByTel(String tel);
    Patient findPatientByCode(String code);
}
