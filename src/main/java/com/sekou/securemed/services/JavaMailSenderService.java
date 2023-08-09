package com.sekou.securemed.services;

import com.sekou.securemed.entities.Hopital;
import com.sekou.securemed.repositories.HopitalRepository;
import com.sekou.securemed.repositories.UtilisateurRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class JavaMailSenderService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private HopitalRepository hopitalRepository;
    @Autowired
    HopitalService hopitalService;
    public void sendEmail(String toEmail, String subject, String body){

        SimpleMailMessage mailMessage = new SimpleMailMessage();
       //mailMessage.setFrom(hopital.getEmail());
        mailMessage.setFrom("sowsekou091@gmail.com");
       mailMessage.setTo(toEmail);
       mailMessage.setText(body);
       mailMessage.setSubject(subject);
       mailSender.send(mailMessage);
       System.out.println("Mail sent successfully...");
    }
}
