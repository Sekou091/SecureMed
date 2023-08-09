package com.sekou.securemed.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ActiverOuDesactiverUtilisateurData {

    private String username;
    private boolean etat;
}
