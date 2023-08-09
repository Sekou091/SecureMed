package com.sekou.securemed.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String roleName;
    //@JsonIgnore
    //@ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    //private Collection<Utilisateur> utilisateurs = new ArrayList<>();
}
