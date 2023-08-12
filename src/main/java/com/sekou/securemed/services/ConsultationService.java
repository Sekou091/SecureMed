package com.sekou.securemed.services;

import com.sekou.securemed.entities.Consultation;
import com.sekou.securemed.entities.RendezVous;
import com.sekou.securemed.repositories.ConsultationRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class ConsultationService {
    @Autowired
    private ConsultationRepository consultationRepository;

    public List<Consultation> getAllConsultations() {
        return consultationRepository.findAll();
    }

    public Optional<Consultation> getConsultationById(Long id) {
        return consultationRepository.findById(id);
    }

    public Consultation addConsultation(Consultation consultation) {
        return consultationRepository.save(consultation);
    }

    public Consultation updateConsultation(Long id, Consultation updatedConsultation) {
        Optional<Consultation> optionalConsultation = consultationRepository.findById(id);

        if (optionalConsultation.isPresent()) {
            Consultation existingConsultation = optionalConsultation.get();
            existingConsultation.setDateConsultation(updatedConsultation.getDateConsultation());
            existingConsultation.setHeureConsultation(updatedConsultation.getHeureConsultation());



            return consultationRepository.save(existingConsultation);
        } else {
            throw new RuntimeException("Consultation non trouvée");
        }
    }

    public void deleteConsultation(Long id) {
        consultationRepository.deleteById(id);
    }
}
