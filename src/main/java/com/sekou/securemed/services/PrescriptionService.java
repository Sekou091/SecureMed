package com.sekou.securemed.services;

import com.sekou.securemed.entities.Consultation;
import com.sekou.securemed.entities.Prescription;
import com.sekou.securemed.repositories.PrescriptionRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class PrescriptionService {
    @Autowired
    private PrescriptionRepository prescriptionRepository;

    public List<Prescription> getAllPrescriptions() {
        return prescriptionRepository.findAll();
    }

    public Optional<Prescription> getPrescriptionById(Long id) {
        return prescriptionRepository.findById(id);
    }

    public Prescription addPrescription(Prescription prescription) {
        return prescriptionRepository.save(prescription);
    }

    public Prescription updatePrescription(Long id, Prescription updatedPrescription) {
        Optional<Prescription> optionalPrescription = prescriptionRepository.findById(id);

        if (optionalPrescription.isPresent()) {
            Prescription existingPrescription = optionalPrescription.get();
            existingPrescription.setDatePrescription(updatedPrescription.getDatePrescription());
            existingPrescription.setPosologie(updatedPrescription.getPosologie());
            existingPrescription.setQuantite(updatedPrescription.getQuantite());
            return prescriptionRepository.save(existingPrescription);
        } else {
            throw new RuntimeException("Prescription non trouvée");
        }
    }

    public void deletePrescription(Long id) {
        prescriptionRepository.deleteById(id);
    }
}

