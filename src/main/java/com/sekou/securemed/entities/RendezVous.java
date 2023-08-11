package com.sekou.securemed.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class RendezVous {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.TIME)
    private Date dateRendezVous;
    @Temporal(TemporalType.TIME)
    private Date heureRendezVous;
    @OneToOne(mappedBy = "rendezVous")
    private Consultation consultation;
    @ManyToOne
    private Patient patient;
    @ManyToOne
    private Medecin medecin;
}
