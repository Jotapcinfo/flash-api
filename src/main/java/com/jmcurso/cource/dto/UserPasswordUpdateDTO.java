package com.jmcurso.cource.dto;

import jakarta.validation.constraints.NotBlank;

public class UserPasswordUpdateDTO {

	@NotBlank(message = "A senha antiga é obrigatória.")
	private String oldPassword;

	@NotBlank(message = "A nova senha é obrigatória.")
	private String newPassword;

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}
