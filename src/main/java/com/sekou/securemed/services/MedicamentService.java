package com.sekou.securemed.services;

import com.sekou.securemed.entities.Medicament;
import com.sekou.securemed.entities.Prescription;
import com.sekou.securemed.repositories.MedicamentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class MedicamentService {

    private MedicamentRepository medicamentRepository;

    public List<Medicament> getAllMedicaments() {
        return medicamentRepository.findAll();
    }

    public Optional<Medicament> getMedicamentById(Long id) {
        return medicamentRepository.findById(id);
    }

    public Medicament addMedicament(Medicament medicament) {
        return medicamentRepository.save(medicament);
    }

    public Medicament updateMedicament(Long id, Medicament updatedMedicament) {
        Optional<Medicament> optionalMedicament = medicamentRepository.findById(id);

        if (optionalMedicament.isPresent()) {
            Medicament existingMedicament = optionalMedicament.get();
            existingMedicament.setCode(updatedMedicament.getCode());
            existingMedicament.setNom(updatedMedicament.getNom());
            existingMedicament.setPrix(updatedMedicament.getPrix());
            return medicamentRepository.save(existingMedicament);
        } else {
            throw new RuntimeException("Médicament non trouvé");
        }
    }

    public void deleteMedicament(Long id) {
        medicamentRepository.deleteById(id);
    }
}
