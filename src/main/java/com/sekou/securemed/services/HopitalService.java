package com.sekou.securemed.services;

import com.sekou.securemed.entities.Hopital;
import com.sekou.securemed.repositories.HopitalRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class HopitalService{

    @Autowired
    private HopitalRepository hopitalRepository;

    //ajouter un hopital
//    @Override
//    public Hopital addHopital(Hopital hopital){
//
//        Hopital hopital1 = hopitalRepository.findHopitalByNomHopital(hopital.getNomHopital());
//        if(hopital1 != null) throw new RuntimeException("Cet hopital existe déjà");
//        hopital1 = Hopital.builder()
//                .nomHopital(hopital.getNomHopital())
//                .tel(hopital.getTel())
//                .ville(hopital.getVille())
//                .email(hopital.getEmail())
//                .adresse(hopital.getAdresse())
//                .codePostal(hopital.getCodePostal())
//                .build();
//        Hopital savedHopital = hopitalRepository.save(hopital1);
//        return savedHopital;
//    }

    public Hopital addHopital(Hopital hopital){

        Hopital hopital1 = hopitalRepository.findHopitalByNomHopital(hopital.getNomHopital());

        if(hopital1 != null) throw new RuntimeException(String.format("L'hopital %s existe déjà", hopital.getNomHopital()));
        try{
            return hopitalRepository.save(hopital);
        }catch(Exception e){

            throw new RuntimeException("Erreur d'enregistrement de l'hopital");
        }
    }

    String returnHopitalEmail(String nomHopital){

        Hopital hopital = hopitalRepository.findHopitalByEmail(nomHopital);

        if(hopital == null) {
            return "Cet hopital n'existe pas!";
        }else {
            return hopitalRepository.findHopitalByNomHopital(nomHopital).getEmail();
        }

    }
}
