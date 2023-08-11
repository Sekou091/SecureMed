package com.sekou.securemed.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Receptionniste extends Personne{
    @Column(unique = true, nullable = false)
    private String code;
    @Temporal(TemporalType.DATE)
    private Date dateEmbauche;
}
