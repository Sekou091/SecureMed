package com.sekou.securemed.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class MaladieChronique extends Maladie{

    private String duree;
    private String progression;
    @OneToMany(mappedBy = "maladieChronique")
    private Collection<Type> type;
}

