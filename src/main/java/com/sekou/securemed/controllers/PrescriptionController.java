package com.sekou.securemed.controllers;

import com.sekou.securemed.entities.Consultation;
import com.sekou.securemed.entities.Prescription;
import com.sekou.securemed.services.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/prescription")
public class PrescriptionController {

    private PrescriptionService prescriptionService;
    @Autowired
    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Prescription>> getAllPrescriptions() {
        List<Prescription> prescriptionList = prescriptionService.getAllPrescriptions();
        return ResponseEntity.ok(prescriptionList);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Prescription> getPrescriptionById(@PathVariable Long id) {
        Optional<Prescription> prescription = prescriptionService.getPrescriptionById(id);
        return prescription.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Prescription> addPrescription(@RequestBody Prescription prescription) {
        Prescription addedPrescription = prescriptionService.addPrescription(prescription);
        return ResponseEntity.ok(addedPrescription);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Prescription> updatePrescription(
            @PathVariable Long id, @RequestBody Prescription updatedPrescription) {
        try {
            Prescription updated = prescriptionService.updatePrescription(id, updatedPrescription);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            System.err.println("Erreur de mise à jour de la prescription");
            return ResponseEntity.badRequest().body(new Prescription());
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Void> deletePrescription(@PathVariable Long id) {
        prescriptionService.deletePrescription(id);
        return ResponseEntity.noContent().build();
    }
}
