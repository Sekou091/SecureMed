package com.sekou.securemed.controllers;

import com.sekou.securemed.entities.Patient;
import com.sekou.securemed.services.PatientService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/patient")
@Data @NoArgsConstructor @AllArgsConstructor
public class PatientController {

    @Autowired
    PatientService patientService;
    @PostMapping("/add-patient")
    public ResponseEntity<?> addPatient(@RequestBody Patient patient) {
        try {
            Patient addedPatient = patientService.addPatient(patient);
            return ResponseEntity.ok(addedPatient);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients = patientService.getAllPatients();
        return ResponseEntity.ok(patients);
    }

    @PostMapping("update/{id}")
    public ResponseEntity<Patient> updatePatient(
            @PathVariable Long id, @RequestBody Patient updatedPatient) {
        try {
            Patient updated = patientService.updatePatient(id, updatedPatient);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("delete/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
