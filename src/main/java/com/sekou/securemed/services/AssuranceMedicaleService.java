package com.sekou.securemed.services;

import com.sekou.securemed.entities.AssuranceMedicale;
import com.sekou.securemed.repositories.AssuranceMedicaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssuranceMedicaleService {

    private AssuranceMedicaleRepository assuranceMedicaleRepository;

    @Autowired
    public AssuranceMedicaleService(AssuranceMedicaleRepository assuranceMedicaleRepository) {
        this.assuranceMedicaleRepository = assuranceMedicaleRepository;
    }

    public AssuranceMedicale addAssuranceMedicale(AssuranceMedicale assuranceMedicale) {
        return assuranceMedicaleRepository.save(assuranceMedicale);
    }

    public List<AssuranceMedicale> getAllAssuranceMedicales() {
        return assuranceMedicaleRepository.findAll();
    }

    public Optional<AssuranceMedicale> getAssuranceMedicaleById(Long id) {
        return assuranceMedicaleRepository.findById(id);
    }

    public void deleteAssuranceMedicale(Long id) {
        assuranceMedicaleRepository.deleteById(id);
    }

    public AssuranceMedicale updateAssuranceMedicale(Long id, AssuranceMedicale updatedAssuranceMedicale) {
        Optional<AssuranceMedicale> optionalAssuranceMedicale = assuranceMedicaleRepository.findById(id);

        if (optionalAssuranceMedicale.isPresent()) {
            AssuranceMedicale existingAssuranceMedicale = optionalAssuranceMedicale.get();
            existingAssuranceMedicale.setCode(updatedAssuranceMedicale.getCode());
            existingAssuranceMedicale.setNomAssureur(updatedAssuranceMedicale.getNomAssureur());
            return assuranceMedicaleRepository.save(existingAssuranceMedicale);
        } else {
            throw new RuntimeException("Assurance Medicale non trouvée");
        }
    }
}

