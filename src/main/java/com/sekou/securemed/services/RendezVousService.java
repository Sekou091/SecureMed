package com.sekou.securemed.services;

import com.sekou.securemed.entities.Caissier;
import com.sekou.securemed.entities.RendezVous;
import com.sekou.securemed.repositories.RendezVousRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class RendezVousService {

    @Autowired
    private RendezVousRepository rendezVousRepository;

    public List<RendezVous> getAllRendezVous() {
        return rendezVousRepository.findAll();
    }

    public Optional<RendezVous> getRendezVousById(Long id) {
        return rendezVousRepository.findById(id);
    }

    public RendezVous addRendezVous(RendezVous rendezVous) {
        return rendezVousRepository.save(rendezVous);
    }

    public RendezVous updateRendezVous(Long id, RendezVous updatedRendezVous) {

        Optional<RendezVous> optionalRendezVous = rendezVousRepository.findById(id);

        if (optionalRendezVous.isPresent()) {
            RendezVous existingRendezVous = optionalRendezVous.get();
            existingRendezVous.setDateRendezVous(updatedRendezVous.getDateRendezVous());
            existingRendezVous.setHeureRendezVous(updatedRendezVous.getHeureRendezVous());
            existingRendezVous.setMedecin(updatedRendezVous.getMedecin());

            return rendezVousRepository.save(existingRendezVous);
        } else {
            throw new RuntimeException("RendezVous non trouvé");
        }
    }

    public void deleteRendezVous(Long id) {
        rendezVousRepository.deleteById(id);
    }
}
