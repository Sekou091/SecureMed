package com.sekou.securemed.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Hopital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String nomHopital;
    private String adresse;
    private String codePostal;
    @Column(unique = true, nullable = false)
    private String tel;
    @Column(unique = true, nullable = false)
    private String email;
    private String ville;
    @OneToMany(mappedBy = "hopital")
    private Collection<Ticket> ticket;
    @OneToMany(mappedBy = "hopital")
    private Collection<Service> service;
}
