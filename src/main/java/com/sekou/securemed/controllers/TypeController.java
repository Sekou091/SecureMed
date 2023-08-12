package com.sekou.securemed.controllers;

import com.sekou.securemed.entities.Type;
import com.sekou.securemed.services.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/types")
public class TypeController {

    private final TypeService typeService;

    @Autowired
    public TypeController(TypeService typeService) {
        this.typeService = typeService;
    }

    @PostMapping("/add")
    public ResponseEntity<Type> addType(@RequestBody Type type) {
        Type addedType = typeService.addType(type);
        return ResponseEntity.ok(addedType);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Type>> getAllTypes() {
        List<Type> types = typeService.getAllTypes();
        return ResponseEntity.ok(types);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Type> getTypeById(@PathVariable Long id) {
        Optional<Type> type = typeService.getTypeById(id);
        return type.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Type> updateType(@PathVariable Long id, @RequestBody Type updatedType) {
        try {
            Type updated = typeService.updateType(id, updatedType);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new Type());
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Void> deleteType(@PathVariable Long id) {
        typeService.deleteType(id);
        return ResponseEntity.noContent().build();
    }
}

