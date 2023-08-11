package com.sekou.securemed.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ManyToAny;

import java.util.Collection;
import java.util.Date;
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String posologie;
    @Temporal(TemporalType.DATE)
    private Date datePrescription;
    private String quantite;
    @ManyToOne
    private Consultation consultation;
    @ManyToOne
    private Medicament medicament;
}
