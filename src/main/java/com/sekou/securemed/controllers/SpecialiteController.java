package com.sekou.securemed.controllers;

import com.sekou.securemed.entities.Specialite;
import com.sekou.securemed.services.SpecialiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/specialite")
public class SpecialiteController {

    private final SpecialiteService specialiteService;

    @Autowired
    public SpecialiteController(SpecialiteService specialiteService) {
        this.specialiteService = specialiteService;
    }

    @PostMapping("/add-specialite")
    public ResponseEntity<Specialite> addSpecialite(@RequestBody Specialite specialite) {
        Specialite addedSpecialite = specialiteService.addSpecialite(specialite);
        return ResponseEntity.ok(addedSpecialite);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Specialite>> getAllSpecialites() {
        List<Specialite> specialites = specialiteService.getAllSpecialites();
        return ResponseEntity.ok(specialites);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Specialite> getSpecialiteById(@PathVariable Long id) {
        Optional<Specialite> specialite = specialiteService.getSpecialiteById(id);
        return specialite.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Specialite> updateSpecialite(
            @PathVariable Long id, @RequestBody Specialite updatedSpecialite) {
        Specialite updated = specialiteService.updateSpecialite(id, updatedSpecialite);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSpecialite(@PathVariable Long id) {
        specialiteService.deleteSpecialite(id);
        return ResponseEntity.noContent().build();
    }
}

