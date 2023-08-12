package com.sekou.securemed.controllers;

import com.sekou.securemed.entities.Hopital;
import com.sekou.securemed.services.HopitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/hopital")
public class HopitalController {

    private final HopitalService hopitalService;

    @Autowired
    public HopitalController(HopitalService hopitalService) {
        this.hopitalService = hopitalService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Hopital>> getAllHopitals() {
        List<Hopital> hopitals = hopitalService.getAllHopitals();
        return ResponseEntity.ok(hopitals);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Hopital> getHopitalById(@PathVariable Long id) {
        Optional<Hopital> hopital = hopitalService.getHopitalById(id);
        return hopital.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/save")
    public ResponseEntity<Hopital> saveHopital(@RequestBody Hopital hopital) {
        Hopital savedHopital = hopitalService.saveHopital(hopital);
        return ResponseEntity.ok(savedHopital);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteHopital(@PathVariable Long id) {
        hopitalService.deleteHopital(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Hopital> updateHopital(
            @PathVariable Long id, @RequestBody Hopital updatedHopital) {
        try {
            Hopital updated = hopitalService.updateHopital(id, updatedHopital);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new Hopital());
        }
    }
}
