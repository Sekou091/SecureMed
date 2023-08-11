package com.sekou.securemed.entities;

import jakarta.persistence.Entity;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class ExamenRadiologique extends AnalyseMedicale {

    private String designation;
    private String resultat;


}
