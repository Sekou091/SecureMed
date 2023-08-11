package com.sekou.securemed.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dateConsultation;
    private Date heureConsultation;
    private String synthese;
    @OneToMany(mappedBy = "consultation", fetch = FetchType.EAGER)
    private Collection<Prescription> prescription = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "consultation")
    private Collection<Ticket>ticket;
    @OneToOne
    private RendezVous rendezVous;
    @ManyToOne
    private Patient patient;
    @ManyToOne
    private Motif motif;
    @ManyToMany
    private Collection<Maladie> maladie;
    @OneToMany(mappedBy = "consultation")
    @JsonIgnore
    private Collection<AnalyseMedicale> analyseMedicale;

}
