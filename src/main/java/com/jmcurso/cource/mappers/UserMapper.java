package com.jmcurso.cource.mappers;

import com.jmcurso.cource.dto.UserDTO;
import com.jmcurso.cource.entities.User;

public class UserMapper {

	public static UserDTO toDTO(User entity) {
		if (entity == null)
			return null;
		return new UserDTO(entity.getId(), entity.getName(), entity.getEmail(), entity.getPhone());
	}

	public static User toEntity(UserDTO dto) {
		if (dto == null)
			return null;
		User user = new User();
		user.setId(dto.getId());
		user.setName(dto.getName());
		user.setEmail(dto.getEmail());
		user.setPhone(dto.getPhone());
		return user;
	}
}
