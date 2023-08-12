package com.sekou.securemed.services;

import com.sekou.securemed.entities.MaladieChronique;
import com.sekou.securemed.repositories.MaladieChroniqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaladieChroniqueService {

    private MaladieChroniqueRepository maladieChroniqueRepository;

    @Autowired
    public MaladieChroniqueService(MaladieChroniqueRepository maladieChroniqueRepository) {
        this.maladieChroniqueRepository = maladieChroniqueRepository;
    }

    public MaladieChronique addMaladieChronique(MaladieChronique maladieChronique) {
        return maladieChroniqueRepository.save(maladieChronique);
    }

    public List<MaladieChronique> getAllMaladiesChroniques() {
        return maladieChroniqueRepository.findAll();
    }

    public Optional<MaladieChronique> getMaladieChroniqueById(Long id) {
        return maladieChroniqueRepository.findById(id);
    }

    public void deleteMaladieChronique(Long id) {
        maladieChroniqueRepository.deleteById(id);
    }

    public MaladieChronique updateMaladieChronique(Long id, MaladieChronique updatedMaladieChronique) {
        Optional<MaladieChronique> optionalMaladieChronique = maladieChroniqueRepository.findById(id);

        if (optionalMaladieChronique.isPresent()) {
            MaladieChronique existingMaladieChronique = optionalMaladieChronique.get();
            existingMaladieChronique.setNom(updatedMaladieChronique.getNom());
            existingMaladieChronique.setCode(updatedMaladieChronique.getCode());
            existingMaladieChronique.setDuree(updatedMaladieChronique.getDuree());
            existingMaladieChronique.setProgression(updatedMaladieChronique.getProgression());
            return maladieChroniqueRepository.save(existingMaladieChronique);
        } else {
            throw new RuntimeException("Maladie Chronique non trouvée");
        }
    }
}

