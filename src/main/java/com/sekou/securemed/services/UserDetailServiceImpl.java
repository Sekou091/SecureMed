package com.sekou.securemed.services;

import com.sekou.securemed.entities.Utilisateur;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor @NoArgsConstructor @Builder
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private AccountServices accountServices;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Utilisateur utilisateur = accountServices.loadUtilisateurByUsername(username);
        if(utilisateur == null) throw new UsernameNotFoundException(String.format("L'utilisateur %s introuvable", username));
        if((utilisateur != null) && (!utilisateur.isEtat())) throw new RuntimeException("Cet utilisateur est inactif");
        String[] roles = utilisateur.getRoles().stream().map(u -> u.getRoleName().toUpperCase()).toArray(String[]::new);
        List<SimpleGrantedAuthority> authorities = Arrays.stream(roles)
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());

        UserDetails userDetails = User
                .withUsername(username)
                .password(utilisateur.getPassword())
                .authorities(authorities) // Utiliser "authorities" au lieu de "roles"
                .build();

        return userDetails;
    }
}
