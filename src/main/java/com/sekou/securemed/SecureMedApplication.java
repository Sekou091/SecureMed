package com.sekou.securemed;

import com.sekou.securemed.config.TwilioConfig;
import com.sekou.securemed.entities.Roles;
import com.sekou.securemed.services.AccountServices;
import com.sekou.securemed.services.JavaMailSenderService;
import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
@SpringBootApplication
public class SecureMedApplication {

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	@Autowired
	private JavaMailSenderService javaMailSenderService;
	@Autowired
	private TwilioConfig twilioConfig;
	@PostConstruct
	public void initTwilio(){
		Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
	}
	public static void main(String[] args) {
		SpringApplication.run(SecureMedApplication.class, args);
	}
//
//	@EventListener(ApplicationReadyEvent.class)
//	public void sendMail(){
//		javaMailSenderService.sendEmail("sowsekou@hotmail.com", "Vérification en deux étapes avec OTP", "Bonjour, votre code OTP est 1252");
//	}
	//@Bean
	CommandLineRunner commandLineRunner(AccountServices accountServices){
		return args -> {
//			accountServices.addUser("Sékou Sow", "sowsekou091@gmail.com", "1234", "1234");
			accountServices.addUser("Admin", "sowsekou@hotmail.com", "1234", "1234");
			accountServices.addRole(new Roles(null,"Patient"));
			accountServices.addRole(new Roles(null,"ADMIN"));
//			accountServices.addRole(new Roles(null,"PATIENT"));
//			accountServices.addRoleToUser("Sékou Sow", "Patient");
			accountServices.addRoleToUser("Admin", "ADMIN");
//			accountServices.addRoleToUser("Admin", "Patient");
//			//accountServices.resetPassword("Admin", "sowsekou@hotmail.com", "1234", "12345", "12345", "");
			//accountServices.initiatePasswordReset("Admin");
			//accountServices.resetPassword("Admin", "123456","123456","114412");
			//accountServices.signUp("Agetic","sowsekou091@gmail.com", "1234", "1234","74007045");
			//accountServices.toggleAccount("Agetic", false);

		};


	}
}

