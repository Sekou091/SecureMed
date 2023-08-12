package com.sekou.securemed.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Patient extends Personne{

    private String code;
    @OneToMany(mappedBy = "patient", fetch = FetchType.EAGER)
    private Collection<RendezVous> rendezVous = new ArrayList<>();
    @OneToMany(mappedBy = "patient")
    @JsonIgnore
    private Collection<Consultation> consultation;
    @OneToMany(mappedBy = "patient")
    @JsonIgnore
    private Collection<AnalyseMedicale> analyseMedicale;
    @OneToMany(mappedBy = "patient", fetch = FetchType.EAGER)
    @JsonIgnore
    private Collection<Type> type = new ArrayList<>();
    @OneToMany(mappedBy = "patient")
    @JsonIgnore
    private Collection<Ticket> ticket;
    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    private Collection<Antecedant> antecedant = new ArrayList<>();
    @ManyToMany(mappedBy = "patient", fetch = FetchType.EAGER)
    @JsonIgnore
    private Collection<AssuranceMedicale> assuranceMedicale = new ArrayList<>();

}
