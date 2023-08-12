package com.sekou.securemed.controllers;

import com.sekou.securemed.entities.Antecedant;
import com.sekou.securemed.services.AntecedantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/antecedant")
public class AntecedantController {

    private AntecedantService antecedantService;

    @Autowired
    public AntecedantController(AntecedantService antecedantService) {
        this.antecedantService = antecedantService;
    }
    @PreAuthorize("hasAuthority('ADMIN', 'RECEPTIONNISTE', 'MEDECIN')")
    @PostMapping("/add-antecedant")
    public ResponseEntity<Antecedant> addAntecedant(@RequestBody Antecedant antecedant) {
        Antecedant addedAntecedant = antecedantService.addAntecedant(antecedant);
        return ResponseEntity.ok(addedAntecedant);
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('ADMIN', 'RECEPTIONNISTE', 'CAISSIER', 'PATIENT')")
    public ResponseEntity<List<Antecedant>> getAllAntecedants() {
        List<Antecedant> antecedants = antecedantService.getAllAntecedants();
        return ResponseEntity.ok(antecedants);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Antecedant> getAntecedantById(@PathVariable Long id) {
        Optional<Antecedant> antecedant = antecedantService.getAntecedantById(id);
        return antecedant.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Antecedant> updateAntecedant(
            @PathVariable Long id, @RequestBody Antecedant updatedAntecedant) {
        try {
            Antecedant updated = antecedantService.updateAntecedant(id, updatedAntecedant);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new Antecedant());
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAntecedant(@PathVariable Long id) {
        antecedantService.deleteAntecedant(id);
        return ResponseEntity.noContent().build();
    }
}

