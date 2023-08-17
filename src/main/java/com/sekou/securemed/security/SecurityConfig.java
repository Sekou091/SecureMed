package com.sekou.securemed.security;

import com.sekou.securemed.Filters.JwtAuthenticationFilter;
import com.sekou.securemed.services.UserDetailServiceImpl;
import com.sekou.securemed.services.UtilisateurUserDetails;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig{

    @Autowired
    UserDetailServiceImpl userDetailService;
    @Autowired
    private JwtAuthenticationFilter authFilter;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    //authentication
    public UserDetailsService userDetailsService() {
        return new UserDetailServiceImpl();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        //httpSecurity.formLogin();
        //httpSecurity.authorizeHttpRequests().anyRequest().authenticated();
        //httpSecurity.csrf().disable();
        //httpSecurity.authorizeHttpRequests().requestMatchers("/api/utilisateurs/authenticate");
        //httpSecurity.authorizeHttpRequests().requestMatchers("/api/utilisateurs/**").authenticated().and().formLogin();
        //httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //httpSecurity.userDetailsService(userDetailService);
        //httpSecurity.addFilter(new JwtAuthenticationFilter());
        //return httpSecurity.build();

        return httpSecurity.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/utilisateurs/login").permitAll()
                .requestMatchers("/api/utilisateurs/sign-up").permitAll()
                .requestMatchers("/api/utilisateurs/sendOTPToUser").permitAll()
                .requestMatchers("/api/utilisateurs/validateOTP").permitAll()
                .requestMatchers("/api/utilisateurs/addNewUser").hasAuthority("ADMIN")
                .requestMatchers("/api/medecin/add-medecin").hasAuthority("ADMIN")
                //.requestMatchers("router/sendOTP").permitAll()
                .and()
                .authorizeHttpRequests().requestMatchers("/api/**")
                .authenticated().and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }
}
