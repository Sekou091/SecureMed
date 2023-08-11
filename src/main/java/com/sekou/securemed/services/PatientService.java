package com.sekou.securemed.services;

import com.sekou.securemed.entities.Patient;
import com.sekou.securemed.repositories.MedecinRepository;
import com.sekou.securemed.repositories.PatientRepository;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service @Transactional
@Data @NoArgsConstructor
public class PatientService {
    private AccountServices accountServices;
    private AccountServicesImpl accountServices1;
    private PatientRepository patientRepository;

    @Autowired
    public PatientService(AccountServices accountServices, AccountServicesImpl accountServices1, PatientRepository patientRepository) {
        this.accountServices = accountServices;
        this.accountServices1 = accountServices1;
        this.patientRepository = patientRepository;
    }

    public Patient addPatient(Patient patient){
        Patient patient1 = patientRepository.findPatientByTel(patient.getTel());
        if(patient1 != null) {
            throw new RuntimeException("Ce patient existe déjà");
        }

        String baseCode = "PAT";
        String randomCode;
        String codePatient = null;

        boolean codeExists = true;
        while (codeExists) {
            randomCode = RandomStringUtils.randomNumeric(4);
            codePatient = baseCode + randomCode + patient.getSexe().charAt(0);
            Patient existingPatientWithCode = patientRepository.findPatientByCode(codePatient);
            if (existingPatientWithCode == null) {
                codeExists = false;
            }
        }

        if (codePatient == null) {
            throw new RuntimeException("Erreur lors de la génération du code du patient");
        }

        patient.setCode(codePatient);
        return patientRepository.save(patient);
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
    }

    public void deletePatient(Long id) {patientRepository.deleteById(id);
    }

    public Patient updatePatient(Long id, Patient updatedPatient) {
        Optional<Patient> optionalPatient = patientRepository.findById(id);

        if (optionalPatient.isPresent()) {
            Patient existingPatient = optionalPatient.get();
            existingPatient.setNom(updatedPatient.getNom());
            existingPatient.setPrenom(updatedPatient.getPrenom());
            existingPatient.setDateNaissance(updatedPatient.getDateNaissance());
            existingPatient.setTel(updatedPatient.getTel());
            existingPatient.setSexe(updatedPatient.getSexe());
            existingPatient.setAdresse(updatedPatient.getAdresse());
            existingPatient.setEmail(updatedPatient.getEmail());
            existingPatient.setSituationMatrimoniale(updatedPatient.getSituationMatrimoniale());
            existingPatient.setNomMere(updatedPatient.getNomMere());
            existingPatient.setNomPere(updatedPatient.getNomPere());
            existingPatient.setNomPersonneAPrevenir(updatedPatient.getNomPersonneAPrevenir());
            existingPatient.setTelPersonneAPrevenir(updatedPatient.getTelPersonneAPrevenir());
            return patientRepository.save(existingPatient);
        } else {
            throw new RuntimeException("Patient non trouvé");
        }
    }


}
