package com.sekou.securemed.controllers;

import com.sekou.securemed.dto.AuthErrorResponse;
import com.sekou.securemed.dto.AuthRequest;
import com.sekou.securemed.dto.RequestOTPForPassword;
import com.sekou.securemed.dto.ResponseOTPForPassword;
import com.sekou.securemed.entities.*;
import com.sekou.securemed.repositories.TokenRevocationRepository;
import com.sekou.securemed.repositories.UtilisateurRepository;
import com.sekou.securemed.services.AccountServicesImpl;
import com.sekou.securemed.services.JwtService;
import com.sekou.securemed.services.TokenRevocationService;
import com.sekou.securemed.services.TwilioOTPService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@Data @NoArgsConstructor  @Builder
@RequestMapping("/api/utilisateurs/")
public class AccountController {

    private AccountServicesImpl accountServices;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;
    private TwilioOTPService twilioOTPService;
    private UtilisateurRepository utilisateurRepository;
    private PasswordEncoder passwordEncoder;
    private TokenRevocationService tokenRevocationService;
    private TokenRevocationRepository tokenRevocationRepository;
    @Autowired
    public AccountController(AccountServicesImpl accountServices, JwtService jwtService, AuthenticationManager authenticationManager, TwilioOTPService twilioOTPService, UtilisateurRepository utilisateurRepository, PasswordEncoder passwordEncoder, TokenRevocationService tokenRevocationService, TokenRevocationRepository tokenRevocationRepository) {
        this.accountServices = accountServices;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.twilioOTPService = twilioOTPService;
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenRevocationService = tokenRevocationService;
        this.tokenRevocationRepository =tokenRevocationRepository;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("loadUser/{username}")
    public ResponseEntity<Utilisateur> loadUser(@PathVariable String username){
        return ResponseEntity.ok(accountServices.loadUtilisateurByUsername(username));
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("allUsers")
    public ResponseEntity<List<Utilisateur>> allUsers(){
        return ResponseEntity.ok(accountServices.listOfUsers());
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody String token) {
        boolean alreadyRevoked = tokenRevocationRepository.existsByToken(token);
        if (alreadyRevoked) {
            throw new IllegalArgumentException("Ce token est déjà révoqué.");
        }
        tokenRevocationService.revokeToken(token);
        jwtService.invalidateToken(token);
        return ResponseEntity.ok("Déconnexion réussie");
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        try {
            Utilisateur utilisateur = utilisateurRepository.findUtilisateurByUsername(authRequest.getUsername());
            if(!utilisateur.isEtat()) throw new RuntimeException("Impossible d'y accéder, votre compte est désactivé.");
            if (utilisateur == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthErrorResponse("User not found"));
            }
            String normalizedDbUsername = normalizeUsername(utilisateur.getUsername());
            String normalizedRequestUsername = normalizeUsername(authRequest.getUsername());

            if (!normalizedDbUsername.equals(normalizedRequestUsername)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthErrorResponse("L'authentication a échoué"));
            }
            if (!passwordEncoder.matches(authRequest.getPassword(), utilisateur.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthErrorResponse("L'authentication a échoué"));
            }

            String username = utilisateur.getUsername();
            List<String> roles = utilisateur.getRoles().stream()
                    .map(role -> role.getRoleName())
                    .collect(Collectors.toList());
            String token = jwtService.generateToken(username, roles);

            return ResponseEntity.ok(token);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AuthErrorResponse("Une erreur s'est produite lors de l'authentification"));
        }
    }

    private String normalizeUsername(String username) {
        return username.replaceAll("[^a-zA-Z0-9]", "");
    }


    @PostMapping("/sendOTPToUser")
    public Mono<ResponseEntity<ResponseOTPForPassword>> sendOTPForPasswordReset(@RequestBody RequestOTPForPassword requestOTPForPassword) {
        return twilioOTPService.sendOTPForPasswordReset(requestOTPForPassword)
                .map(responseOTPForPassword -> ResponseEntity.ok(responseOTPForPassword))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/validateOTP")
    public Mono<ResponseEntity<String>> validateOTP(@RequestBody String userInputOtp, @RequestBody String username) {
        return twilioOTPService.validateOTP(userInputOtp, username)
                .map(response -> ResponseEntity.ok(response))
                .defaultIfEmpty(ResponseEntity.status(400).body("Validation de l'OTP échouée"));
    }
    @PostMapping("/addNewUser")
    @PreAuthorize("hasAuthority('ADMIN', 'RECEPTIONNISTE')")
    public ResponseEntity<?> addNewUser(@RequestBody RegistrationRequest registrationRequest){
        try{
            Utilisateur utilisateur = accountServices.addUser(registrationRequest.getUsername(),
                    registrationRequest.getEmail(),
                    registrationRequest.getPassword(),
                    registrationRequest.getConfirmPassword());

            return ResponseEntity.ok(utilisateur);
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Erreur d'enregistrement de l'utilisateur");
        }
    }
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN', 'RECEPTIONNISTE', 'CAISSIER', 'PATIENT', 'MEDECIN')")
    public ResponseEntity<Utilisateur> updateUser(@PathVariable Long id, @RequestBody Utilisateur utilisateur) {
        utilisateur.setId(id);
        Utilisateur updatedUser = accountServices.updateUser(utilisateur);
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("delete/{username}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        accountServices.deleteUser(username);
        return ResponseEntity.ok("Utilisateur supprimé avec succès: " + username);
    }

    @PostMapping("/loadUserByEmail/{email}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Utilisateur> loadUserByEmail(@PathVariable String email){

        return ResponseEntity.ok(accountServices.loadUtilisateurByEmail(email));
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/activerOuDesactiverCompte")
    public void activerOuDesactiverCompte(@RequestBody ActiverOuDesactiverUtilisateurData toggleAccount){

        accountServices.toggleAccount(toggleAccount.getUsername(), toggleAccount.isEtat());
    }

    @GetMapping("/loadUserByUsername/{username}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Utilisateur> getUserByUsername(@PathVariable String username) {
        Utilisateur utilisateur = accountServices.loadUtilisateurByUsername(username);
        return ResponseEntity.ok(utilisateur);
    }

    @PostMapping("/roles/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Roles> addRole(@RequestBody Roles roles) {
        Roles role = accountServices.addRole(roles);
        return ResponseEntity.ok(role);
    }

    @PostMapping("/roles/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Roles> updateRole(@PathVariable Long id, @RequestBody Roles roles) {
        roles.setId(id);
        Roles updatedRole = accountServices.updateRole(id, roles);
        return ResponseEntity.ok(updatedRole);
    }

    @PostMapping("/roles/delete/{roleName}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteRole(@PathVariable String roleName) {
        accountServices.deleteRole(roleName);
        return ResponseEntity.ok("Role supprimé avec succès: " + roleName);
    }

    @GetMapping("/roles/list-roles")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Roles>> listOfRoles() {
        List<Roles> roles = accountServices.listOfRoles();
        return ResponseEntity.ok(roles);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody SignUpRequest signUpRequest) {
        accountServices.signUp(signUpRequest.getUsername(), signUpRequest.getEmail(), signUpRequest.getPassword(), signUpRequest.getConfirmPassword(), signUpRequest.getTelephone());
        return ResponseEntity.ok("Inscription réussie");
    }

    @PostMapping("/initiate-password-reset")
    public ResponseEntity<String> initiatePasswordReset(@RequestBody UsernameRequest usernameRequest) {
        accountServices.initiatePasswordReset(usernameRequest.getUsername());
        return ResponseEntity.ok("Réinitialisation de mot de passe initiée");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        accountServices.resetPassword(resetPasswordRequest.getUsername(), resetPasswordRequest.getNewPassword(), resetPasswordRequest.getConfirmNewPassword(), resetPasswordRequest.getOtp());
        return ResponseEntity.ok("Mot de passe réinitialisé avec succès");
    }

    @PostMapping("/add-role-to-user")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> addRoleToUser(@RequestBody UserRoleRequest userRoleRequest) {
        accountServices.addRoleToUser(userRoleRequest.getUsername(), userRoleRequest.getRoleName());
        return ResponseEntity.ok("Rôle ajouté à l'utilisateur avec succès");
    }

    @PostMapping("/remove-role-to-user")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> removeRoleToUser(@RequestBody UserRoleRequest userRoleRequest) {
        accountServices.removeRoleToUser(userRoleRequest.getUsername(), userRoleRequest.getRoleName());
        return ResponseEntity.ok("Rôle retiré de l'utilisateur avec succès");
    }
}

