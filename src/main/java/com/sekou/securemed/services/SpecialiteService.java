package com.sekou.securemed.services;

import com.sekou.securemed.entities.Specialite;
import com.sekou.securemed.repositories.SpecialiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class SpecialiteService {

    private SpecialiteRepository specialiteRepository;

    @Autowired
    public SpecialiteService(SpecialiteRepository specialiteRepository) {
        this.specialiteRepository = specialiteRepository;
    }

    public Specialite addSpecialite(Specialite specialite) {
        return specialiteRepository.save(specialite);
    }

    public List<Specialite> getAllSpecialites() {
        return specialiteRepository.findAll();
    }

    public Optional<Specialite> getSpecialiteById(Long id) {
        return specialiteRepository.findById(id);
    }

    public void deleteSpecialite(Long id) {
        specialiteRepository.deleteById(id);
    }

    public Specialite updateSpecialite(Long id, Specialite updatedSpecialite) {
        Optional<Specialite> optionalSpecialite = specialiteRepository.findById(id);

        if (optionalSpecialite.isPresent()) {
            Specialite existingSpecialite = optionalSpecialite.get();
            existingSpecialite.setLibelle(updatedSpecialite.getLibelle());
            return specialiteRepository.save(existingSpecialite);
        } else {
            throw new RuntimeException("Spécialité non trouvée");
        }
    }
}
