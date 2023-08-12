package com.sekou.securemed.controllers;

import com.sekou.securemed.entities.Service;
import com.sekou.securemed.services.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/service")
public class ServiceController {

    private ServiceService serviceService;

    @Autowired
    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @PostMapping("/add-service")
    public ResponseEntity<Service> addService(@RequestBody Service service) {
        Service addedService = serviceService.addService(service);
        return ResponseEntity.ok(addedService);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Service>> getAllServices() {
        List<Service> services = serviceService.getAllServices();
        return ResponseEntity.ok(services);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Service> getServiceById(@PathVariable Long id) {
        Optional<Service> service = serviceService.getServiceById(id);
        return service.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Service> updateService(
            @PathVariable Long id, @RequestBody Service updatedService) {
        Service updated = serviceService.updateService(id, updatedService);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        serviceService.deleteService(id);
        return ResponseEntity.noContent().build();
    }
}
