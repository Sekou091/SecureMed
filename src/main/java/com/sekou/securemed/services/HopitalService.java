package com.sekou.securemed.services;

import com.sekou.securemed.entities.Hopital;
import com.sekou.securemed.repositories.HopitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HopitalService {

    private HopitalRepository hopitalRepository;

    @Autowired
    public HopitalService(HopitalRepository hopitalRepository) {
        this.hopitalRepository = hopitalRepository;
    }

    public List<Hopital> getAllHopitals() {
        return hopitalRepository.findAll();
    }

    public Optional<Hopital> getHopitalById(Long id) {
        return hopitalRepository.findById(id);
    }

    public Hopital saveHopital(Hopital hopital) {
        return hopitalRepository.save(hopital);
    }

    public void deleteHopital(Long id) {
        hopitalRepository.deleteById(id);
    }

    public Hopital updateHopital(Long id, Hopital updatedHopital) {
        Optional<Hopital> optionalHopital = hopitalRepository.findById(id);

        if (optionalHopital.isPresent()) {
            Hopital existingHopital = optionalHopital.get();
            existingHopital.setNomHopital(updatedHopital.getNomHopital());
            existingHopital.setAdresse(updatedHopital.getAdresse());
            existingHopital.setEmail(updatedHopital.getEmail());
            existingHopital.setCodePostal(updatedHopital.getCodePostal());
            existingHopital.setTel(updatedHopital.getTel());
            existingHopital.setVille(updatedHopital.getVille());

            return hopitalRepository.save(existingHopital);
        } else {
            throw new RuntimeException("Hôpital non trouvé");
        }
    }
}
