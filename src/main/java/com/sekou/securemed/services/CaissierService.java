package com.sekou.securemed.services;

import com.sekou.securemed.entities.Caissier;
import com.sekou.securemed.repositories.CaissierRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
@Transactional
@Data @NoArgsConstructor
public class CaissierService {

    private CaissierRepository caissierRepository;
    private AccountServicesImpl accountServices;
    @Autowired
    public CaissierService(CaissierRepository caissierRepository, AccountServicesImpl accountServices) {
        this.caissierRepository = caissierRepository;
        this.accountServices = accountServices;
    }

    public Caissier addCaissier(Caissier caissier) {
        // Vérifier si le caissier existe déjà
        Caissier existingCaissier = caissierRepository.findCaissierByTel(caissier.getTel());
        if (existingCaissier != null) {
            throw new RuntimeException("Ce caissier existe déjà");
        }

        // Générer le code du caissier
        String baseCode = "CAI";
        String randomCode;
        String codeCaissier = null; // Initialiser la variable

        boolean codeExists = true;
        while (codeExists) {
            randomCode = RandomStringUtils.randomNumeric(4);
            codeCaissier = baseCode + randomCode;
            Caissier existingCaissierWithCode = caissierRepository.findCaissierByCode(codeCaissier);
            if (existingCaissierWithCode == null) {
                codeExists = false;
            }
        }

        if (codeCaissier == null) {
            throw new RuntimeException("Erreur lors de la génération du code du caissier");
        }

        caissier.setCode(codeCaissier);

        // Enregistrer le caissier et ajouter les rôles nécessaires
        caissier = caissierRepository.save(caissier);
        String username = RandomStringUtils.randomAlphanumeric(6);
        String password = RandomStringUtils.randomAlphabetic(6);
        accountServices.addUser(username, caissier.getEmail(), password, password);
        accountServices.addRoleToUser(username, "CAISSIER");

        return caissier;
    }

    public List<Caissier> getAllCaissiers() {
        return caissierRepository.findAll();
    }

    public Optional<Caissier> getCaissierById(Long id) {
        return caissierRepository.findById(id);
    }

    public Caissier updateCaissier(Long id, Caissier updatedCaissier) {
        Optional<Caissier> optionalCaissier = caissierRepository.findById(id);

        if (optionalCaissier.isPresent()) {
            Caissier existingCaissier = optionalCaissier.get();
            // Mettre à jour les attributs du caissier avec les nouvelles valeurs
            existingCaissier.setNom(updatedCaissier.getNom());
            existingCaissier.setPrenom(updatedCaissier.getPrenom());
            existingCaissier.setDateNaissance(updatedCaissier.getDateNaissance());
            existingCaissier.setTel(updatedCaissier.getTel());
            existingCaissier.setSexe(updatedCaissier.getSexe());
            existingCaissier.setDateEmbauche(updatedCaissier.getDateEmbauche());
            existingCaissier.setEmail(updatedCaissier.getEmail());
            existingCaissier.setAdresse(updatedCaissier.getAdresse());
            existingCaissier.setNomMere(updatedCaissier.getNomMere());
            existingCaissier.setNomPere(updatedCaissier.getNomPere());
            existingCaissier.setNomPersonneAPrevenir(updatedCaissier.getNomPersonneAPrevenir());
            existingCaissier.setTelPersonneAPrevenir(updatedCaissier.getTelPersonneAPrevenir());
            existingCaissier.setSituationMatrimoniale(updatedCaissier.getSituationMatrimoniale());
            return caissierRepository.save(existingCaissier);
        } else {
            throw new RuntimeException("Caissier non trouvé");
        }
    }

    public void deleteCaissier(Long id) {
        caissierRepository.deleteById(id);
    }
}
