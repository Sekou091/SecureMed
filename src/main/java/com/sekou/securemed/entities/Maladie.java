package com.sekou.securemed.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data @NoArgsConstructor @AllArgsConstructor
public class Maladie {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(unique = true, nullable = false)
    private String nom;
    @Column(unique = true, nullable = false)
    private String code;
    @ManyToMany(mappedBy = "maladie")
    @JsonIgnore
    private Collection<Consultation> consultation;
    @ManyToMany
    private Collection<AnalyseMedicale> analyseMedicale;
}
