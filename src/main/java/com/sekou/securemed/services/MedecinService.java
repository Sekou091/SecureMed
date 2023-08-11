package com.sekou.securemed.services;

import com.sekou.securemed.entities.Medecin;
import com.sekou.securemed.repositories.MedecinRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service @Transactional
@Data @NoArgsConstructor @AllArgsConstructor
public class MedecinService {

    @Autowired
    private MedecinRepository medecinRepository;
    @Autowired
    private AccountServicesImpl accountServices;
    public Medecin addMedecin(Medecin medecin){
        Medecin medecin1 = medecinRepository.findMedecinByEmail(medecin.getEmail());
        if(medecin1 != null) {
            throw new RuntimeException("Ce médecin existe déjà");
        }

        String username = RandomStringUtils.randomAlphanumeric(6);
        String password = RandomStringUtils.randomAlphabetic(6);
        accountServices.addUser(username, medecin.getEmail(), password, password);
        accountServices.addRoleToUser(username, "MEDECIN");

        String baseCode = "MED";
        String randomCode;
        String codeMedecin = null; // Initialize the variable

        boolean codeExists = true;
        while (codeExists) {
            randomCode = RandomStringUtils.randomNumeric(4);
            codeMedecin = baseCode + randomCode + medecin.getSexe().charAt(0);
            Medecin existingMedecinWithCode = medecinRepository.findMedecinByCode(codeMedecin);
            if (existingMedecinWithCode == null) {
                codeExists = false;
            }
        }

        if (codeMedecin == null) {
            throw new RuntimeException("Erreur lors de la génération du code du médecin");
        }

        medecin.setCode(codeMedecin);
        return medecinRepository.save(medecin);
    }

    public List<Medecin> getAllMedecins() {
        return medecinRepository.findAll();
    }

    public Optional<Medecin> getMedecinById(Long id) {
        return medecinRepository.findById(id);
    }

    public void deleteMedecin(Long id) {
        medecinRepository.deleteById(id);
    }

    public Medecin updateMedecin(Long id, Medecin updatedMedecin) {
        Optional<Medecin> optionalMedecin = medecinRepository.findById(id);

        if (optionalMedecin.isPresent()) {
            Medecin existingMedecin = optionalMedecin.get();
            existingMedecin.setNom(updatedMedecin.getNom());
            existingMedecin.setPrenom(updatedMedecin.getPrenom());
            existingMedecin.setDateNaissance(updatedMedecin.getDateNaissance());
            existingMedecin.setTel(updatedMedecin.getTel());
            existingMedecin.setSexe(updatedMedecin.getSexe());
            existingMedecin.setSpecialite(updatedMedecin.getSpecialite());
            existingMedecin.setDateEmbauche(updatedMedecin.getDateEmbauche());
            existingMedecin.setService(updatedMedecin.getService());
            existingMedecin.setAdresse(updatedMedecin.getAdresse());
            existingMedecin.setEmail(updatedMedecin.getEmail());
            existingMedecin.setSituationMatrimoniale(updatedMedecin.getSituationMatrimoniale());
            existingMedecin.setNomMere(updatedMedecin.getNomMere());
            existingMedecin.setNomPere(updatedMedecin.getNomPere());
            existingMedecin.setNomPersonneAPrevenir(updatedMedecin.getNomPersonneAPrevenir());
            existingMedecin.setTelPersonneAPrevenir(updatedMedecin.getTelPersonneAPrevenir());
            return medecinRepository.save(existingMedecin);
        } else {
            throw new RuntimeException("Médecin non trouvé");
        }
    }

}
