package com.sekou.securemed.controllers;

import com.sekou.securemed.entities.Medecin;
import com.sekou.securemed.services.MedecinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:8085")
@RequestMapping("/api/medecin")
public class MedecinController {
    private MedecinService medecinService;

    @Autowired
    public MedecinController(MedecinService medecinService) {
        this.medecinService = medecinService;
    }

    @PostMapping("/add-medecin")
    public ResponseEntity<?> addMedecin(@RequestBody Medecin medecin) {
        try {
            Medecin addedMedecin = medecinService.addMedecin(medecin);
            return ResponseEntity.ok(addedMedecin);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<Medecin>> getAllMedecins() {
        List<Medecin> medecins = medecinService.getAllMedecins();
        return ResponseEntity.ok(medecins);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Medecin> getMedecinById(@PathVariable Long id) {
        Optional<Medecin> medecin = medecinService.getMedecinById(id);
        return medecin.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Medecin> updateMedecin(
            @PathVariable Long id, @RequestBody Medecin updatedMedecin) {
        try {
            Medecin updated = medecinService.updateMedecin(id, updatedMedecin);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new Medecin());
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMedecin(@PathVariable Long id) {
        medecinService.deleteMedecin(id);
        return ResponseEntity.noContent().build();
    }

}