package com.jmcurso.cource.dto;

import com.jmcurso.cource.entities.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserDTO {

	private Long id;

	@NotBlank(message = "Nome é obrigatório")
	@Size(max = 30, message = "Nome deve ter no máximo 60 caracteres")
	private String name;

	@Email(message = "Email inválido")
	@NotBlank(message = "Email é obrigatório")
	@Size(max = 30, message = "Email deve ter no máximo 100 caracteres")
	private String email;

	@NotBlank(message = "Telefone é obrigatório")
	@Size(max = 30, message = "Telefone deve ter no máximo 20 caracteres")
	private String phone;

	public UserDTO() {
	}

	public UserDTO(Long id, String name, String email, String phone) {

		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
	}

	public UserDTO(User user) {
		id = user.getId();
		name = user.getName();
		email = user.getEmail();
		phone = user.getPhone();
	}

	public User toEntity() {
		User user = new User();
		user.setId(this.id);
		user.setName(this.name);
		user.setEmail(this.email);
		user.setPhone(this.phone);
		return user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
