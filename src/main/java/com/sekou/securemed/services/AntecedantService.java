package com.sekou.securemed.services;

import com.sekou.securemed.entities.Antecedant;
import com.sekou.securemed.repositories.AntecedantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AntecedantService {

    private AntecedantRepository antecedantRepository;

    @Autowired
    public AntecedantService(AntecedantRepository antecedantRepository) {
        this.antecedantRepository = antecedantRepository;
    }

    public Antecedant addAntecedant(Antecedant antecedant) {
        return antecedantRepository.save(antecedant);
    }

    public List<Antecedant> getAllAntecedants() {
        return antecedantRepository.findAll();
    }

    public Optional<Antecedant> getAntecedantById(Long id) {
        return antecedantRepository.findById(id);
    }

    public void deleteAntecedant(Long id) {
        antecedantRepository.deleteById(id);
    }

    public Antecedant updateAntecedant(Long id, Antecedant updatedAntecedant) {
        Optional<Antecedant> optionalAntecedant = antecedantRepository.findById(id);

        if (optionalAntecedant.isPresent()) {
            Antecedant existingAntecedant = optionalAntecedant.get();
            existingAntecedant.setNom(updatedAntecedant.getNom());
            return antecedantRepository.save(existingAntecedant);
        } else {
            throw new RuntimeException("Antecedant non trouvé");
        }
    }
}

