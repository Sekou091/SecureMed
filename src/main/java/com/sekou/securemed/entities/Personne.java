package com.sekou.securemed.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.common.value.qual.MinLen;

import java.util.Date;
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Personne {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    @Column(nullable = false)
    private String nom;
    @Column(nullable = false)
    private String prenom;
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dateNaissance;
    @Column(nullable = false)
    private String tel;
    @Column(unique = true, nullable = false)
    private String situationMatrimoniale;
    @Column(nullable = false)
    private String adresse;
    @Column(nullable = false)
    private String sexe;
    @Column(nullable = false)
    private String nomPere;
    @Column(nullable = false)
    private String nomMere;
    @Column(nullable = false)
    private String nomPersonneAPrevenir;
    @Column(nullable = false)
    private String telPersonneAPrevenir;
}
