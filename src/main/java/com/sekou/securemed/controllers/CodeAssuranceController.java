package com.sekou.securemed.controllers;

import com.sekou.securemed.entities.CodeAssurance;
import com.sekou.securemed.services.CodeAssuranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/code-assurance")
public class CodeAssuranceController {

    private CodeAssuranceService codeAssuranceService;

    @Autowired
    public CodeAssuranceController(CodeAssuranceService codeAssuranceService) {
        this.codeAssuranceService = codeAssuranceService;
    }

    @PostMapping("/add-code-assurance")
    public ResponseEntity<CodeAssurance> addCodeAssurance(@RequestBody CodeAssurance codeAssurance) {
        CodeAssurance addedCodeAssurance = codeAssuranceService.addCodeAssurance(codeAssurance);
        return ResponseEntity.ok(addedCodeAssurance);
    }

    @GetMapping("/list")
    public ResponseEntity<List<CodeAssurance>> getAllCodeAssurances() {
        List<CodeAssurance> codeAssurances = codeAssuranceService.getAllCodeAssurances();
        return ResponseEntity.ok(codeAssurances);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<CodeAssurance> getCodeAssuranceById(@PathVariable Long id) {
        Optional<CodeAssurance> codeAssurance = codeAssuranceService.getCodeAssuranceById(id);
        return codeAssurance.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<CodeAssurance> updateCodeAssurance(
            @PathVariable Long id, @RequestBody CodeAssurance updatedCodeAssurance) {
        try {
            CodeAssurance updated = codeAssuranceService.updateCodeAssurance(id, updatedCodeAssurance);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new CodeAssurance());
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCodeAssurance(@PathVariable Long id) {
        codeAssuranceService.deleteCodeAssurance(id);
        return ResponseEntity.noContent().build();
    }
}

