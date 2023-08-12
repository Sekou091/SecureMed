package com.sekou.securemed.services;

import com.sekou.securemed.entities.ExamenBiologique;
import com.sekou.securemed.entities.ExamenRadiologique;
import com.sekou.securemed.repositories.ExamenBiologiqueRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class ExamenBiologiqueService {

    private final ExamenBiologiqueRepository examenBiologiqueRepository;

    public List<ExamenBiologique> getAllExamensBiologiques() {
        return examenBiologiqueRepository.findAll();
    }

    public Optional<ExamenBiologique> getExamenBiologiqueById(Long id) {
        return examenBiologiqueRepository.findById(id);
    }

    public ExamenBiologique addExamenBiologique(ExamenBiologique examenBiologique) {
        return examenBiologiqueRepository.save(examenBiologique);
    }

    public ExamenBiologique updateExamenBiologique(Long id, ExamenBiologique updatedExamenBiologique) {
        Optional<ExamenBiologique> optionalExamenBiologique = examenBiologiqueRepository.findById(id);

        if (optionalExamenBiologique.isPresent()) {
            ExamenBiologique existingExamenBiologique = optionalExamenBiologique.get();
            existingExamenBiologique.setDesignation(updatedExamenBiologique.getDesignation());
            existingExamenBiologique.setResultatExamen(updatedExamenBiologique.getResultatExamen());
            existingExamenBiologique.setDateTraitement(updatedExamenBiologique.getDateTraitement());
            existingExamenBiologique.setPrixTraitement(updatedExamenBiologique.getPrixTraitement());
            return examenBiologiqueRepository.save(existingExamenBiologique);
        } else {
            throw new RuntimeException("Examen biologique non trouvé");
        }
    }

    public void deleteExamenBiologique(Long id) {
        examenBiologiqueRepository.deleteById(id);
    }
}