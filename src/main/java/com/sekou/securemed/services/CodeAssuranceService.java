package com.sekou.securemed.services;

import com.sekou.securemed.entities.CodeAssurance;
import com.sekou.securemed.repositories.CodeAssuranceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CodeAssuranceService {

    private CodeAssuranceRepository codeAssuranceRepository;

    @Autowired
    public CodeAssuranceService(CodeAssuranceRepository codeAssuranceRepository) {
        this.codeAssuranceRepository = codeAssuranceRepository;
    }

    public CodeAssurance addCodeAssurance(CodeAssurance codeAssurance) {
        return codeAssuranceRepository.save(codeAssurance);
    }

    public List<CodeAssurance> getAllCodeAssurances() {
        return codeAssuranceRepository.findAll();
    }

    public Optional<CodeAssurance> getCodeAssuranceById(Long id) {
        return codeAssuranceRepository.findById(id);
    }

    public void deleteCodeAssurance(Long id) {
        codeAssuranceRepository.deleteById(id);
    }

    public CodeAssurance updateCodeAssurance(Long id, CodeAssurance updatedCodeAssurance) {
        Optional<CodeAssurance> optionalCodeAssurance = codeAssuranceRepository.findById(id);

        if (optionalCodeAssurance.isPresent()) {
            CodeAssurance existingCodeAssurance = optionalCodeAssurance.get();
            existingCodeAssurance.setCodeAssureur(updatedCodeAssurance.getCodeAssureur());
            existingCodeAssurance.setEtat(updatedCodeAssurance.isEtat());
            return codeAssuranceRepository.save(existingCodeAssurance);
        } else {
            throw new RuntimeException("CodeAssurance non trouvé");
        }
    }
}

