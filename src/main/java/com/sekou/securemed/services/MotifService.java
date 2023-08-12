package com.sekou.securemed.services;

import com.sekou.securemed.entities.Motif;
import com.sekou.securemed.repositories.MotifRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class MotifService {

    private MotifRepository motifRepository;
    @Autowired
    public MotifService(MotifRepository motifRepository) {
        this.motifRepository = motifRepository;
    }

    public Motif addMotif(Motif motif) {
        return motifRepository.save(motif);
    }

    public List<Motif> getAllMotifs() {
        return motifRepository.findAll();
    }

    public Optional<Motif> getMotifById(Long id) {
        return motifRepository.findById(id);
    }

    public Motif updateMotif(Long id, Motif updatedMotif) {
        Optional<Motif> optionalMotif = motifRepository.findById(id);

        if (optionalMotif.isPresent()) {
            Motif existingMotif = optionalMotif.get();
            existingMotif.setLibelle(updatedMotif.getLibelle());
            return motifRepository.save(existingMotif);
        } else {
            throw new RuntimeException("Motif non trouvé");
        }
    }

    public void deleteMotif(Long id) {
        motifRepository.deleteById(id);
    }
}

