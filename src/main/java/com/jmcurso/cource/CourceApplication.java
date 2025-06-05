package com.jmcurso.cource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.jmcurso.cource.entities.User;
import com.jmcurso.cource.entities.enums.ProfileEnum;
import com.jmcurso.cource.repositories.UserRepository;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
	    info = @Info(title = "API Flash - Jefferson Moreno", version = "2.0", description = "Documentação da API")
	)

@SpringBootApplication
public class CourceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourceApplication.class, args);
	}
	
	@Bean
	CommandLineRunner init(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			if (userRepository.findByEmail("admin@starlabs.com").isEmpty()) {
				User admin = new User();
				admin.setEmail("admin@starlabs.com");
				admin.setName("Admin");
				admin.setPassword(passwordEncoder.encode("123456"));
				admin.setProfile(ProfileEnum.ROLE_ADMIN);

				userRepository.save(admin);
				System.out.println("✅ Usuário ADMIN criado com sucesso!");
			} else {
				System.out.println("ℹ️ Usuário ADMIN já existe.");
			}
		};
	}
}
