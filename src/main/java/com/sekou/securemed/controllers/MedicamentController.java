package com.sekou.securemed.controllers;

import com.sekou.securemed.entities.Medicament;
import com.sekou.securemed.entities.Prescription;
import com.sekou.securemed.services.MedicamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/medicament")
public class MedicamentController {

    private MedicamentService medicamentService;
    @Autowired
    public MedicamentController(MedicamentService medicamentService) {
        this.medicamentService = medicamentService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Medicament>> getAllMedicaments() {
        List<Medicament> medicamentList = medicamentService.getAllMedicaments();
        return ResponseEntity.ok(medicamentList);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Medicament> getMedicamentById(@PathVariable Long id) {
        Optional<Medicament> medicament = medicamentService.getMedicamentById(id);
        return medicament.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Medicament> addMedicament(@RequestBody Medicament medicament) {
        Medicament addedMedicament = medicamentService.addMedicament(medicament);
        return ResponseEntity.ok(addedMedicament);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Medicament> updateMedicament(
            @PathVariable Long id, @RequestBody Medicament updatedMedicament) {
        try {
            Medicament updated = medicamentService.updateMedicament(id, updatedMedicament);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            System.err.println("Erreur de mise à jour du médicament");
            return ResponseEntity.badRequest().body(new Medicament());
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMedicament(@PathVariable Long id) {
        medicamentService.deleteMedicament(id);
        return ResponseEntity.noContent().build();
    }
}
