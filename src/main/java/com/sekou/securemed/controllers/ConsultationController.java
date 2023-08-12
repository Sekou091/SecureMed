package com.sekou.securemed.controllers;

import com.sekou.securemed.entities.Consultation;
import com.sekou.securemed.entities.RendezVous;
import com.sekou.securemed.services.ConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/consultation")
public class ConsultationController {

    private ConsultationService consultationService;
    @Autowired
    public ConsultationController(ConsultationService consultationService) {
        this.consultationService = consultationService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Consultation>> getAllConsultations() {
        List<Consultation> consultationList = consultationService.getAllConsultations();
        return ResponseEntity.ok(consultationList);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Consultation> getConsultationById(@PathVariable Long id) {
        Optional<Consultation> consultation = consultationService.getConsultationById(id);
        return consultation.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Consultation> addConsultation(@RequestBody Consultation consultation) {
        Consultation addedConsultation = consultationService.addConsultation(consultation);
        return ResponseEntity.ok(addedConsultation);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Consultation> updateConsultation(
            @PathVariable Long id, @RequestBody Consultation updatedConsultation) {
        try {
            Consultation updated = consultationService.updateConsultation(id, updatedConsultation);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            System.err.println("Erreur de mise à jour de la consultation");
            return ResponseEntity.badRequest().body(new Consultation());
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Void> deleteConsultation(@PathVariable Long id) {
        consultationService.deleteConsultation(id);
        return ResponseEntity.noContent().build();
    }
}
