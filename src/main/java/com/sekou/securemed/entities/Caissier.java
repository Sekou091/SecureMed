package com.sekou.securemed.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
@EqualsAndHashCode(callSuper = true)
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Caissier extends Personne{
    @Column(unique = true, nullable = false)
    private String code;
    @Temporal(TemporalType.DATE)
    private Date dateEmbauche;
    @OneToMany(mappedBy = "caissier", fetch = FetchType.EAGER)
    private Collection<Ticket> ticket = new ArrayList<>();
}
