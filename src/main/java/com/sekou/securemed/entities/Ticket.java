package com.sekou.securemed.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String libelle;
    private int prix;
    @ManyToOne
    private  Hopital hopital;
    @ManyToOne
    private Ticket ticket;
    @ManyToOne
    private Caissier caissier;
    @ManyToOne
    private Consultation consultation;
    @ManyToOne
    private Patient patient;
}
