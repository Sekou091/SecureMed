package com.sekou.securemed.controllers;

import com.sekou.securemed.entities.Caissier;
import com.sekou.securemed.entities.RendezVous;
import com.sekou.securemed.services.RendezVousService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/rendezvous")
public class RendezVousController {

    private RendezVousService rendezVousService;
    @Autowired
    public RendezVousController(RendezVousService rendezVousService) {
        this.rendezVousService = rendezVousService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<RendezVous>> getAllRendezVous() {
        List<RendezVous> rendezVousList = rendezVousService.getAllRendezVous();
        return ResponseEntity.ok(rendezVousList);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<RendezVous> getRendezVousById(@PathVariable Long id) {
        Optional<RendezVous> rendezVous = rendezVousService.getRendezVousById(id);
        return rendezVous.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<RendezVous> addRendezVous(@RequestBody RendezVous rendezVous) {
        RendezVous addedRendezVous = rendezVousService.addRendezVous(rendezVous);
        return ResponseEntity.ok(addedRendezVous);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<RendezVous> updateRendezVous(
            @PathVariable Long id, @RequestBody RendezVous updatedRendezVous) {
        try {
            RendezVous updated = rendezVousService.updateRendezVous(id, updatedRendezVous);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            System.err.println("Erreur de mise à jour du Rendez-Vous");
            return ResponseEntity.badRequest().body(new RendezVous());
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRendezVous(@PathVariable Long id) {
        rendezVousService.deleteRendezVous(id);
        return ResponseEntity.noContent().build();
    }
}
