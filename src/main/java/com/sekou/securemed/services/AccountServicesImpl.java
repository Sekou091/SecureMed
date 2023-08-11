package com.sekou.securemed.services;

import com.sekou.securemed.entities.Roles;
import com.sekou.securemed.entities.Utilisateur;
import com.sekou.securemed.repositories.RolesRepository;
import com.sekou.securemed.repositories.UtilisateurRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Transactional
@Data @NoArgsConstructor
public class AccountServicesImpl implements AccountServices {
    private UtilisateurRepository utilisateurRepository;
    private RolesRepository rolesRepository;
    private PasswordEncoder passwordEncoder;
    private JavaMailSenderService javaMailSenderService;
    private TwilioOTPService twilioOTPService;
    private final Map<String, String> otpMap = new ConcurrentHashMap<>();
    @Autowired
    public AccountServicesImpl(UtilisateurRepository utilisateurRepository, RolesRepository rolesRepository, PasswordEncoder passwordEncoder, JavaMailSenderService javaMailSenderService, TwilioOTPService twilioOTPService) {
        this.utilisateurRepository = utilisateurRepository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
        this.javaMailSenderService = javaMailSenderService;
        this.twilioOTPService = twilioOTPService;
    }

    @Override
    public Utilisateur addUser(String username, String email, String password, String confirmPassword) {
        Utilisateur utilisateur = utilisateurRepository.findUtilisateurByUsername(username);
        if (utilisateur != null) throw new RuntimeException("Cet utilisateur existe déjà!");
        if (!password.equals(confirmPassword)) throw new RuntimeException("Passwords not matches");
        utilisateur = Utilisateur.builder()
                .username(username)
                .email(email)
                .dateCreation(new Date())
                .etat(true)
                .password(passwordEncoder.encode(password))
                .roles(new ArrayList<>())
                .build();
        String passwordNonHache;
        passwordNonHache = confirmPassword;
        Utilisateur savedUser = utilisateurRepository.save(utilisateur);
        String destinationEmail = utilisateur.getEmail();
        String body, subject;
        subject = String.format("Création de compte SecureMed");
        body = String.format("Félicitation %s ! Votre compte SecureMed a été créé avec succès. Votre nom d'utilisateur est : %s et votre mot de passe est : %s. " +
                "Merci de ne communiquer vos informations à aucune autre personne. En cas de problème, merci de contacter notre service d'aide à service_it@securemed.ml", utilisateur.getUsername(), utilisateur.getUsername(), passwordNonHache);
        javaMailSenderService.sendEmail(destinationEmail, subject, body);
        return savedUser;

    }

    @Override
    public Utilisateur updateUser(Utilisateur utilisateur) {
        Optional<Utilisateur> existingUserOptional = utilisateurRepository.findById(utilisateur.getId());

        if (existingUserOptional.isPresent()) {
            Utilisateur existingUser = existingUserOptional.get();
            existingUser.setUsername(utilisateur.getUsername());
            existingUser.setEmail(utilisateur.getEmail());

            if (utilisateur.getPassword() != null && !utilisateur.getPassword().isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
            }

            try {
                Utilisateur savedUser = utilisateurRepository.save(existingUser);
                System.out.println("Mise à jour effectuée avec succès !");
                return savedUser;
            } catch (Exception e) {
                System.err.println("Erreur lors de la mise à jour de l'utilisateur : " + e.getMessage());
                throw new RuntimeException("Erreur lors de la mise à jour de l'utilisateur");
            }
        } else {
            throw new RuntimeException("Cet utilisateur n'existe pas !");
        }
    }


    @Override
    public void deleteUser(String username) {
        Utilisateur utilisateur = utilisateurRepository.findUtilisateurByUsername(username);
        if (utilisateur == null) throw new IllegalArgumentException("Utilisateur non trouvé!");
        utilisateurRepository.delete(utilisateur);
        utilisateurRepository.flush();
        System.out.println("Utilisateur supprimé avec succès: " + username);

    }


    @Override
    public List<Utilisateur> listOfUsers() {
        return utilisateurRepository.findAll();
    }

    @Override
    public Utilisateur loadUtilisateurByEmail(String email) {
        Utilisateur utilisateur = utilisateurRepository.findUtilisateurByEmail(email);

        if(utilisateur != null){
            try{
                return utilisateurRepository.findUtilisateurByEmail(email);
            }catch(Exception e){
                System.err.println("Un problème est surevenu lors du chargement de l'utilisateur " + e.getMessage());
                throw new RuntimeException("Erreur lors du chargement de l'utilisateur");
            }
        }else{
            throw new IllegalArgumentException("Utilisateur non trouvé!");
        }
    }

    @Override
    public void toggleAccount(String username, boolean etat) {
        Utilisateur utilisateur = utilisateurRepository.findUtilisateurByUsername(username);
        if(utilisateur == null){
            throw new IllegalArgumentException("Utilisateur non trouvé avec le nom d'utilisateur : "+ username);
        }

        utilisateur.setEtat(etat);
        utilisateurRepository.save(utilisateur);
    }

    @Override
    public Utilisateur loadUtilisateurByUsername(String username) {
        return utilisateurRepository.findUtilisateurByUsername(username);
    }

    @Override
    public Roles addRole(Roles roles) {
        Roles roles1 = rolesRepository.findRoleByRoleName(roles.getRoleName());
        if(roles1 != null) throw new RuntimeException("Ce role existe déjà!");
        roles1 = Roles.builder()
                .roleName(roles.getRoleName())
                .build();
        Roles savedRole = rolesRepository.save(roles1);
        return savedRole;
    }

    @Override
    public Roles updateRole(Long id, Roles updatedRole) {
        Optional<Roles> existingRoleOptional = rolesRepository.findById(id);

        if (existingRoleOptional.isPresent()) {
            Roles existingRole = existingRoleOptional.get();
            existingRole.setRoleName(updatedRole.getRoleName()); // Mettez à jour le nom du rôle si nécessaire

            Roles savedRole = rolesRepository.saveAndFlush(existingRole);
            System.out.println("Mise à jour effectuée avec succès!");
            return savedRole;
        } else {
            throw new IllegalArgumentException("Role non trouvé!");
        }
    }



    @Override
    public void deleteRole(String roleName) {
        Roles role = rolesRepository.findRoleByRoleName(roleName);
        if (role != null) {
            try {
                rolesRepository.delete(role);
                rolesRepository.flush();
                System.out.println("Role supprimé avec succès: " + roleName);
            } catch (Exception e) {
                System.err.println("Un problème est surevenu lors de la suppression du role " + e.getMessage());
                throw new RuntimeException("Erreur lors de la suppression du role");
            }
        } else {
            throw new IllegalArgumentException("Role non trouvé!");
        }
    }

    @Override
    public List<Roles> listOfRoles() {
        return rolesRepository.findAll();
    }

    @Override
    public void signUp(String username, String email, String password, String confirmPassword, String telephone) {
        Utilisateur utilisateur = utilisateurRepository.findUtilisateurByUsername(username);

        if(utilisateur != null) throw new RuntimeException("Cet utilisateur existe déjà.");
        if(!password.equals(confirmPassword)) throw new IllegalArgumentException("Passwords doesn't match");
        Roles patientRole = rolesRepository.findRoleByRoleName("PATIENT");
        if(patientRole == null){
            throw new RuntimeException("Ce role n'existe pas.");
        }

        Collection<Roles> roles = new ArrayList<>();
        roles.add(patientRole);
        utilisateur = Utilisateur.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .etat(true)
                .dateCreation(new Date())
                .roles(roles)
                .build();
        utilisateurRepository.save(utilisateur);
        String decodedPassword = confirmPassword;
        String destinationEmail = utilisateur.getEmail();
        String body, subject;
        subject = String.format("Création de compte SecureMed");
        body = String.format("Félicitation %s ! Votre compte SecureMed a été créé avec succès. Votre nom d'utilisateur est : %s et votre mot de passe est : %s", utilisateur.getUsername(), utilisateur.getUsername(), decodedPassword);
        javaMailSenderService.sendEmail(destinationEmail, subject, body);

    }
    @Override
    public void initiatePasswordReset(String username) {
        Utilisateur utilisateur = utilisateurRepository.findUtilisateurByUsername(username);
        if (utilisateur == null) {
            throw new RuntimeException("Cet utilisateur n'existe pas!");
        }

        String generatedOTP = twilioOTPService.generateOTP();
        otpMap.put(username, generatedOTP);

        String destinationEmail = utilisateur.getEmail();
        String subject = "Réinitialisation de mot de passe SecureMed";
        String body = String.format("Bonjour, Merci d'entrer le code OTP %s pour vérifier que c'est bien vous.", generatedOTP);
        javaMailSenderService.sendEmail(destinationEmail, subject, body);
    }

    @Override
    public void resetPassword(String username, String newPassword, String confirmNewPassword, String otp) {
        Utilisateur utilisateur = utilisateurRepository.findUtilisateurByUsername(username);
        if (utilisateur == null) {
            throw new RuntimeException("Cet utilisateur n'existe pas!");
        }

        String storedOTP = otpMap.get(username);
        if (storedOTP == null || !storedOTP.equals(otp)) {
            throw new IllegalArgumentException("Code OTP invalide");
        }

        if (!newPassword.equals(confirmNewPassword)) {
            throw new RuntimeException("Les nouveaux mots de passe ne correspondent pas");
        }

        utilisateur.setPassword(passwordEncoder.encode(newPassword));
        utilisateurRepository.save(utilisateur);

        // Supprimez l'OTP stocké après utilisation
        otpMap.remove(username);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {

        Utilisateur utilisateur = utilisateurRepository.findUtilisateurByUsername(username);
        Roles roles = rolesRepository.findRoleByRoleName(roleName);
        utilisateur.getRoles().add(roles);
    }

    @Override
    public void removeRoleToUser(String username, String roleName) {
        Utilisateur utilisateur = utilisateurRepository.findUtilisateurByUsername(username);
        Roles roles = rolesRepository.findRoleByRoleName(roleName);
        utilisateur.getRoles().remove(roles);
    }

}
