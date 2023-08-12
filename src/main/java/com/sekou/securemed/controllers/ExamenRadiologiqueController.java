package com.sekou.securemed.controllers;

import com.sekou.securemed.entities.ExamenRadiologique;
import com.sekou.securemed.entities.Medicament;
import com.sekou.securemed.services.ExamenRadiologiqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/examen-radiologique")
public class ExamenRadiologiqueController {

    private ExamenRadiologiqueService examenRadiologiqueService;
    @Autowired
    public ExamenRadiologiqueController(ExamenRadiologiqueService examenRadiologiqueService) {
        this.examenRadiologiqueService = examenRadiologiqueService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<ExamenRadiologique>> getAllExamensRadiologiques() {
        List<ExamenRadiologique> examenRadiologiqueList = examenRadiologiqueService.getAllExamensRadiologiques();
        return ResponseEntity.ok(examenRadiologiqueList);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ExamenRadiologique> getExamenRadiologiqueById(@PathVariable Long id) {
        Optional<ExamenRadiologique> examenRadiologique = examenRadiologiqueService.getExamenRadiologiqueById(id);
        return examenRadiologique.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<ExamenRadiologique> addExamenRadiologique(@RequestBody ExamenRadiologique examenRadiologique) {
        ExamenRadiologique addedExamenRadiologique = examenRadiologiqueService.addExamenRadiologique(examenRadiologique);
        return ResponseEntity.ok(addedExamenRadiologique);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<ExamenRadiologique> updateExamenRadiologique(
            @PathVariable Long id, @RequestBody ExamenRadiologique updatedExamenRadiologique) {
        try {
            ExamenRadiologique updated = examenRadiologiqueService.updateExamenRadiologique(id, updatedExamenRadiologique);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            System.err.println("Erreur de mise à jour de l'examen radiologique");
            return ResponseEntity.badRequest().body(new ExamenRadiologique());
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Void> deleteExamenRadiologique(@PathVariable Long id) {
        examenRadiologiqueService.deleteExamenRadiologique(id);
        return ResponseEntity.noContent().build();
    }
}