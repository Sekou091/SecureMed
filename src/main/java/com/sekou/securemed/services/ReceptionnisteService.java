package com.sekou.securemed.services;

import com.sekou.securemed.entities.Caissier;
import com.sekou.securemed.entities.Receptionniste;
import com.sekou.securemed.repositories.CaissierRepository;
import com.sekou.securemed.repositories.ReceptionnisteRepository;
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
public class ReceptionnisteService {

    @Autowired
    private ReceptionnisteRepository receptionnisteRepository;
    @Autowired
    private AccountServicesImpl accountServices;

    public Receptionniste addReceptionniste(Receptionniste receptionniste) {
        // Vérifier si le receptionniste existe déjà
        Receptionniste existingReceptionniste = receptionnisteRepository.findReceptionnisteByTel(receptionniste.getTel());
        if (existingReceptionniste != null) {
            throw new RuntimeException("Ce receptionniste existe déjà");
        }

        // Générer le code du receptionniste
        String baseCode = "REC";
        String randomCode;
        String codeReceptionniste = null; // Initialiser la variable

        boolean codeExists = true;
        while (codeExists) {
            randomCode = RandomStringUtils.randomNumeric(4);
            codeReceptionniste = baseCode + randomCode;
            Receptionniste existingReceptionnisteWithCode = receptionnisteRepository.findReceptionnisteByCode(codeReceptionniste);
            if (existingReceptionnisteWithCode == null) {
                codeExists = false;
            }
        }

        if (codeReceptionniste == null) {
            throw new RuntimeException("Erreur lors de la génération du code du réceptionniste");
        }

        receptionniste.setCode(codeReceptionniste);

        // Enregistrer le receptionniste et ajouter les rôles nécessaires
        receptionniste = receptionnisteRepository.save(receptionniste);
        String username = RandomStringUtils.randomAlphanumeric(6);
        String password = RandomStringUtils.randomAlphabetic(6);
        accountServices.addUser(username, receptionniste.getEmail(), password, password);
        accountServices.addRoleToUser(username, "RECEPTIONNISTE");

        return receptionniste;
    }

    public List<Receptionniste> getAllReceptionnistes() {
        return receptionnisteRepository.findAll();
    }

    public Optional<Receptionniste> getReceptionnisteById(Long id) {
        return receptionnisteRepository.findById(id);
    }

    public Receptionniste updateReceptionniste(Long id, Receptionniste updatedReceptionniste) {
        Optional<Receptionniste> optionalReceptionniste = receptionnisteRepository.findById(id);

        if (optionalReceptionniste.isPresent()) {
            Receptionniste existingReceptionniste = optionalReceptionniste.get();
            // Mettre à jour les attributs du receptionniste avec les nouvelles valeurs
            existingReceptionniste.setNom(updatedReceptionniste.getNom());
            existingReceptionniste.setPrenom(updatedReceptionniste.getPrenom());
            existingReceptionniste.setDateNaissance(updatedReceptionniste.getDateNaissance());
            existingReceptionniste.setTel(updatedReceptionniste.getTel());
            existingReceptionniste.setSexe(updatedReceptionniste.getSexe());
            existingReceptionniste.setDateEmbauche(updatedReceptionniste.getDateEmbauche());
            existingReceptionniste.setEmail(updatedReceptionniste.getEmail());
            existingReceptionniste.setAdresse(updatedReceptionniste.getAdresse());
            existingReceptionniste.setNomMere(updatedReceptionniste.getNomMere());
            existingReceptionniste.setNomPere(updatedReceptionniste.getNomPere());
            existingReceptionniste.setNomPersonneAPrevenir(updatedReceptionniste.getNomPersonneAPrevenir());
            existingReceptionniste.setTelPersonneAPrevenir(updatedReceptionniste.getTelPersonneAPrevenir());
            existingReceptionniste.setSituationMatrimoniale(updatedReceptionniste.getSituationMatrimoniale());
            return receptionnisteRepository.save(existingReceptionniste);
        } else {
            throw new RuntimeException("Receptionniste non trouvé");
        }
    }

    public void deleteReceptionniste(Long id) {
        receptionnisteRepository.deleteById(id);
    }

}
