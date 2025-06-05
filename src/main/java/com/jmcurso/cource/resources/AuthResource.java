package com.jmcurso.cource.resources;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmcurso.cource.dto.LoginRequestDTO;
import com.jmcurso.cource.dto.RegisterRequestDTO;
import com.jmcurso.cource.dto.ResponseDTO;
import com.jmcurso.cource.dto.ResponseRegDTO;
import com.jmcurso.cource.entities.User;
import com.jmcurso.cource.infra.security.TokenService;
import com.jmcurso.cource.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthResource {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final TokenService tokenService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequestDTO body) {
		User user = userRepository.findByEmail(body.email())
				.orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

		if (passwordEncoder.matches(body.password(), user.getPassword())) {
			String token = tokenService.generateToken(user);
			return ResponseEntity.ok(new ResponseDTO(user.getId(), user.getName(), user.getEmail(), user.getPhone(), token));

		}

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterRequestDTO body) {
		Optional<User> userExists = userRepository.findByEmail(body.email());

		if (userExists.isPresent()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Email já cadastrado");
		}

		User newUser = new User();
		newUser.setName(body.name());
		newUser.setEmail(body.email());
		newUser.setPassword(passwordEncoder.encode(body.password()));
		newUser.setPhone("99999999");
		userRepository.save(newUser);

		return ResponseEntity.ok(new ResponseRegDTO(newUser.getId(), newUser.getName(), newUser.getEmail(), newUser.getPhone()));
	}
}
