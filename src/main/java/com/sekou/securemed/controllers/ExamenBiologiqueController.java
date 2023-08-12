package com.sekou.securemed.controllers;

import com.sekou.securemed.entities.ExamenBiologique;
import com.sekou.securemed.entities.ExamenRadiologique;
import com.sekou.securemed.services.ExamenBiologiqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/examen-biologique")
public class ExamenBiologiqueController {

    private ExamenBiologiqueService examenBiologiqueService;
    @Autowired
    public ExamenBiologiqueController(ExamenBiologiqueService examenBiologiqueService) {
        this.examenBiologiqueService = examenBiologiqueService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<ExamenBiologique>> getAllExamensBiologiques() {
        List<ExamenBiologique> examenBiologiqueList = examenBiologiqueService.getAllExamensBiologiques();
        return ResponseEntity.ok(examenBiologiqueList);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ExamenBiologique> getExamenBiologiqueById(@PathVariable Long id) {
        Optional<ExamenBiologique> examenBiologique = examenBiologiqueService.getExamenBiologiqueById(id);
        return examenBiologique.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<ExamenBiologique> addExamenBiologique(@RequestBody ExamenBiologique examenBiologique) {
        ExamenBiologique addedExamenBiologique = examenBiologiqueService.addExamenBiologique(examenBiologique);
        return ResponseEntity.ok(addedExamenBiologique);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<ExamenBiologique> updateExamenBiologique(
            @PathVariable Long id, @RequestBody ExamenBiologique updatedExamenBiologique) {
        try {
            ExamenBiologique updated = examenBiologiqueService.updateExamenBiologique(id, updatedExamenBiologique);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            System.err.println("Erreur de mise à jour de l'examen biologique");
            return ResponseEntity.badRequest().body(new ExamenBiologique());
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Void> deleteExamenBiologique(@PathVariable Long id) {
        examenBiologiqueService.deleteExamenBiologique(id);
        return ResponseEntity.noContent().build();
    }
}
