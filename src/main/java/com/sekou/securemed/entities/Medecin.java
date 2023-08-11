package com.sekou.securemed.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Medecin extends Personne{
    @Column(unique = true, nullable = false)
    private String code;
    @Temporal(TemporalType.DATE)
    private Date dateEmbauche;
    @OneToMany(mappedBy = "medecin")
    private Collection<RendezVous> rendezVous;
    @ManyToOne
    private Specialite specialite;
    @ManyToOne
    private Service service;
    @OneToMany(mappedBy = "medecin", fetch = FetchType.EAGER)
    private Collection<AnalyseMedicale> analyseMedicale = new ArrayList<>();
}
