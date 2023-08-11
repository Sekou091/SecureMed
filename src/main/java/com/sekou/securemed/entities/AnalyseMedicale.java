package com.sekou.securemed.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data @NoArgsConstructor @AllArgsConstructor
public class AnalyseMedicale {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTraitement;
    private int prixTraitement;
    @ManyToOne
    private Consultation consultation;
    @ManyToMany(mappedBy = "analyseMedicale")
    private Collection<Maladie> maladie;
    @ManyToOne
    private Medecin medecin;
    @ManyToOne
    private Patient patient;
}
