package com.sekou.securemed.controllers;

import com.sekou.securemed.entities.AssuranceMedicale;
import com.sekou.securemed.services.AssuranceMedicaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/assurance-medicale")
public class AssuranceMedicaleController {

    private final AssuranceMedicaleService assuranceMedicaleService;

    @Autowired
    public AssuranceMedicaleController(AssuranceMedicaleService assuranceMedicaleService) {
        this.assuranceMedicaleService = assuranceMedicaleService;
    }

    @PostMapping("/add-assurance-medicale")
    public ResponseEntity<AssuranceMedicale> addAssuranceMedicale(@RequestBody AssuranceMedicale assuranceMedicale) {
        AssuranceMedicale addedAssuranceMedicale = assuranceMedicaleService.addAssuranceMedicale(assuranceMedicale);
        return ResponseEntity.ok(addedAssuranceMedicale);
    }

    @GetMapping("/list")
    public ResponseEntity<List<AssuranceMedicale>> getAllAssuranceMedicales() {
        List<AssuranceMedicale> assuranceMedicales = assuranceMedicaleService.getAllAssuranceMedicales();
        return ResponseEntity.ok(assuranceMedicales);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<AssuranceMedicale> getAssuranceMedicaleById(@PathVariable Long id) {
        Optional<AssuranceMedicale> assuranceMedicale = assuranceMedicaleService.getAssuranceMedicaleById(id);
        return assuranceMedicale.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<AssuranceMedicale> updateAssuranceMedicale(
            @PathVariable Long id, @RequestBody AssuranceMedicale updatedAssuranceMedicale) {
        try {
            AssuranceMedicale updated = assuranceMedicaleService.updateAssuranceMedicale(id, updatedAssuranceMedicale);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new AssuranceMedicale());
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAssuranceMedicale(@PathVariable Long id) {
        assuranceMedicaleService.deleteAssuranceMedicale(id);
        return ResponseEntity.noContent().build();
    }
}

