package com.sekou.securemed.controllers;

import com.sekou.securemed.entities.Motif;
import com.sekou.securemed.services.MotifService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/motifs")
public class MotifController {

    private final MotifService motifService;

    @Autowired
    public MotifController(MotifService motifService) {
        this.motifService = motifService;
    }

    @PostMapping("/add")
    public ResponseEntity<Motif> addMotif(@RequestBody Motif motif) {
        Motif addedMotif = motifService.addMotif(motif);
        return ResponseEntity.ok(addedMotif);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Motif>> getAllMotifs() {
        List<Motif> motifs = motifService.getAllMotifs();
        return ResponseEntity.ok(motifs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Motif> getMotifById(@PathVariable Long id) {
        Optional<Motif> motif = motifService.getMotifById(id);
        return motif.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Motif> updateMotif(@PathVariable Long id, @RequestBody Motif updatedMotif) {
        try {
            Motif updated = motifService.updateMotif(id, updatedMotif);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new Motif());
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMotif(@PathVariable Long id) {
        motifService.deleteMotif(id);
        return ResponseEntity.noContent().build();
    }
}

