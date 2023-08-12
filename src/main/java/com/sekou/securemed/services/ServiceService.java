package com.sekou.securemed.services;

import com.sekou.securemed.entities.Service;
import com.sekou.securemed.repositories.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
@org.springframework.stereotype.Service
public class ServiceService {

    private ServiceRepository serviceRepository;

    @Autowired
    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public Service addService(Service service) {
        return serviceRepository.save(service);
    }

    public List<Service> getAllServices() {
        return serviceRepository.findAll();
    }

    public Optional<Service> getServiceById(Long id) {
        return serviceRepository.findById(id);
    }

    public void deleteService(Long id) {
        serviceRepository.deleteById(id);
    }

    public Service updateService(Long id, Service updatedService) {
        Optional<Service> optionalService = serviceRepository.findById(id);

        if (optionalService.isPresent()) {
            Service existingService = optionalService.get();
            existingService.setNom(updatedService.getNom());
            existingService.setCode(updatedService.getCode());
            existingService.setDateCreation(updatedService.getDateCreation());
            return serviceRepository.save(existingService);
        } else {
            throw new RuntimeException("Service non trouvé");
        }
    }
}
