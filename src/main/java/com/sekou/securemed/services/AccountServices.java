package com.sekou.securemed.services;

import com.sekou.securemed.entities.Roles;
import com.sekou.securemed.entities.Utilisateur;

import java.util.List;

public interface AccountServices {

    // Services pour Utilisateurs

    Utilisateur addUser(String username, String email, String password, String confirmPassword);
    Utilisateur updateUser(Utilisateur utilisateur);
    void deleteUser(String username);

    List<Utilisateur> listOfUsers();
    Utilisateur loadUtilisateurByEmail(String email);
    Utilisateur loadUtilisateurByUsername(String username);
    void addRoleToUser(String username, String roleName);
    void removeRoleToUser(String username, String roleName);
    //Désactiver un compte utilisateur
    void toggleAccount(String username, boolean etat);

    //Services pour Roles
    Roles addRole(Roles roles);
    Roles updateRole(Long id, Roles roles);
    void deleteRole(String roleName);
    List<Roles> listOfRoles();
    //Roles loadRoles(String roleName);

    void signUp(String username, String email, String password, String confirmPassword, String telephone);
    void initiatePasswordReset(String username);
    void resetPassword(String username, String newPassword, String confirmNewPassword, String otp);


}
