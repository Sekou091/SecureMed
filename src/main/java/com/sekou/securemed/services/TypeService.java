package com.sekou.securemed.services;

import com.sekou.securemed.entities.Type;
import com.sekou.securemed.repositories.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TypeService {

    private final TypeRepository typeRepository;

    @Autowired
    public TypeService(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    public Type addType(Type type) {
        return typeRepository.save(type);
    }

    public List<Type> getAllTypes() {
        return typeRepository.findAll();
    }

    public Optional<Type> getTypeById(Long id) {
        return typeRepository.findById(id);
    }

    public Type updateType(Long id, Type updatedType) {
        Optional<Type> optionalType = typeRepository.findById(id);

        if (optionalType.isPresent()) {
            Type existingType = optionalType.get();
            existingType.setCode(updatedType.getCode());
            existingType.setMaladieChronique(updatedType.getMaladieChronique());
            existingType.setPatient(updatedType.getPatient());
            return typeRepository.save(existingType);
        } else {
            throw new RuntimeException("Type non trouvé");
        }
    }

    public void deleteType(Long id) {
        typeRepository.deleteById(id);
    }
}
