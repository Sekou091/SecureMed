package com.sekou.securemed.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    @Column(unique = true, nullable = false)
    private String code;
    @Temporal(TemporalType.DATE)
    private Date dateCreation;
    @OneToMany(mappedBy = "service")
    private Collection<Medecin> medecin = new ArrayList<>();
    @ManyToOne
    private Hopital hopital;
}
