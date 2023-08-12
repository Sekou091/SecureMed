package com.sekou.securemed.services;

import com.sekou.securemed.entities.Consultation;
import com.sekou.securemed.entities.ExamenRadiologique;
import com.sekou.securemed.repositories.ExamenRadiologiqueRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class ExamenRadiologiqueService {
    @Autowired
    private ExamenRadiologiqueRepository examenRadiologiqueRepository;

    public List<ExamenRadiologique> getAllExamensRadiologiques() {
        return examenRadiologiqueRepository.findAll();
    }

    public Optional<ExamenRadiologique> getExamenRadiologiqueById(Long id) {
        return examenRadiologiqueRepository.findById(id);
    }

    public ExamenRadiologique addExamenRadiologique(ExamenRadiologique examenRadiologique) {
        return examenRadiologiqueRepository.save(examenRadiologique);
    }

    public ExamenRadiologique updateExamenRadiologique(Long id, ExamenRadiologique updatedExamenRadiologique) {
        Optional<ExamenRadiologique> optionalExamenRadiologique = examenRadiologiqueRepository.findById(id);

        if (optionalExamenRadiologique.isPresent()) {
            ExamenRadiologique existingExamenRadiologique = optionalExamenRadiologique.get();
            existingExamenRadiologique.setDesignation(updatedExamenRadiologique.getDesignation());
            existingExamenRadiologique.setResultat(updatedExamenRadiologique.getResultat());
            existingExamenRadiologique.setDateTraitement(updatedExamenRadiologique.getDateTraitement());
            existingExamenRadiologique.setPrixTraitement(updatedExamenRadiologique.getPrixTraitement());
            return examenRadiologiqueRepository.save(existingExamenRadiologique);
        } else {
            throw new RuntimeException("Examen radiologique non trouvé");
        }
    }

    public void deleteExamenRadiologique(Long id) {
        examenRadiologiqueRepository.deleteById(id);
    }
}