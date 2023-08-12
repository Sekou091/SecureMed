package com.sekou.securemed.controllers;

import com.sekou.securemed.entities.Caissier;
import com.sekou.securemed.entities.Medecin;
import com.sekou.securemed.services.CaissierService;
import com.sekou.securemed.services.MedecinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/caissier")
public class CaissierController {

    private CaissierService caissierService;

    @Autowired
    public CaissierController(CaissierService caissierService) {
        this.caissierService = caissierService;
    }

    @PostMapping("/add-caissier")
    public ResponseEntity<?> addCaissier(@RequestBody Caissier caissier) {
        try {
            Caissier addedCaissier = caissierService.addCaissier(caissier);
            return ResponseEntity.ok(addedCaissier);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<Caissier>> getAllCaissiers() {
        List<Caissier> caissiers = caissierService.getAllCaissiers();
        return ResponseEntity.ok(caissiers);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Caissier> getCaissierById(@PathVariable Long id) {
        Optional<Caissier> caissier = caissierService.getCaissierById(id);
        return caissier.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Caissier> updateCaissier(
            @PathVariable Long id, @RequestBody Caissier updatedCaissier) {
        try {
            Caissier updated = caissierService.updateCaissier(id, updatedCaissier);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            System.err.println("Erreur de mise à jour du caissier");
            return ResponseEntity.badRequest().body(new Caissier());
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCaissier(@PathVariable Long id) {
        caissierService.deleteCaissier(id);
        return ResponseEntity.noContent().build();
    }


}
