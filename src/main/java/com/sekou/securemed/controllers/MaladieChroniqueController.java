package com.sekou.securemed.controllers;

import com.sekou.securemed.entities.MaladieChronique;
import com.sekou.securemed.services.MaladieChroniqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/maladie-chronique")
@CrossOrigin(origins = "*")
public class MaladieChroniqueController {

    private final MaladieChroniqueService maladieChroniqueService;

    @Autowired
    public MaladieChroniqueController(MaladieChroniqueService maladieChroniqueService) {
        this.maladieChroniqueService = maladieChroniqueService;
    }

    @PostMapping("/add")
    public ResponseEntity<MaladieChronique> addMaladieChronique(@RequestBody MaladieChronique maladieChronique) {
        MaladieChronique addedMaladieChronique = maladieChroniqueService.addMaladieChronique(maladieChronique);
        return ResponseEntity.ok(addedMaladieChronique);
    }

    @GetMapping("/list")
    public ResponseEntity<List<MaladieChronique>> getAllMaladiesChroniques() {
        List<MaladieChronique> maladiesChroniques = maladieChroniqueService.getAllMaladiesChroniques();
        return ResponseEntity.ok(maladiesChroniques);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaladieChronique> getMaladieChroniqueById(@PathVariable Long id) {
        Optional<MaladieChronique> maladieChronique = maladieChroniqueService.getMaladieChroniqueById(id);
        return maladieChronique.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<MaladieChronique> updateMaladieChronique(
            @PathVariable Long id, @RequestBody MaladieChronique updatedMaladieChronique) {
        try {
            MaladieChronique updated = maladieChroniqueService.updateMaladieChronique(id, updatedMaladieChronique);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MaladieChronique());
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMaladieChronique(@PathVariable Long id) {
        maladieChroniqueService.deleteMaladieChronique(id);
        return ResponseEntity.noContent().build();
    }
}

