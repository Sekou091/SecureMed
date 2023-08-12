package com.sekou.securemed.controllers;

import com.sekou.securemed.entities.Caissier;
import com.sekou.securemed.entities.Receptionniste;
import com.sekou.securemed.services.ReceptionnisteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/receptionniste")
public class ReceptionnisteController {
    @Autowired
    private ReceptionnisteService receptionnisteService;
    @PostMapping("/add-receptionniste")
    public ResponseEntity<?> addReceptionniste(@RequestBody Receptionniste receptionniste) {
        try {
            Receptionniste addedReceptionniste = receptionnisteService.addReceptionniste(receptionniste);
            return ResponseEntity.ok(addedReceptionniste);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<Receptionniste>> getAllReceptionnistes() {
        List<Receptionniste> receptionnistes = receptionnisteService.getAllReceptionnistes();
        return ResponseEntity.ok(receptionnistes);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Receptionniste> getReceptionnisteById(@PathVariable Long id) {
        Optional<Receptionniste> receptionniste = receptionnisteService.getReceptionnisteById(id);
        return receptionniste.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Receptionniste> updateReceptionniste(
            @PathVariable Long id, @RequestBody Receptionniste updatedReceptionniste) {
        try {
            Receptionniste updated = receptionnisteService.updateReceptionniste(id, updatedReceptionniste);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            System.err.println("Erreur de mise à jour du receptionniste");
            return ResponseEntity.badRequest().body(new Receptionniste());
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Void> deleteReceptionniste(@PathVariable Long id) {
        receptionnisteService.deleteReceptionniste(id);
        return ResponseEntity.noContent().build();
    }


}
