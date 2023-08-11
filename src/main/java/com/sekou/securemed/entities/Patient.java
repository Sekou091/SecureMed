package com.sekou.securemed.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Patient extends Personne{
    @Column(unique = true, nullable = false)
    private String code;
    @OneToMany(mappedBy = "patient", fetch = FetchType.EAGER)
    private Collection<RendezVous> rendezVous = new ArrayList<>();
    @OneToMany(mappedBy = "patient")
    private Collection<Consultation> consultation;
    @OneToMany(mappedBy = "patient")
    private Collection<AnalyseMedicale> analyseMedicale;
    @OneToMany(mappedBy = "patient", fetch = FetchType.EAGER)
    private Collection<Type> type = new ArrayList<>();
    @OneToMany(mappedBy = "patient")
    private Collection<Ticket> ticket;

}
