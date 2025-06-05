package com.jmcurso.cource.services;

import static com.jmcurso.cource.mappers.UserMapper.toDTO;
import static com.jmcurso.cource.mappers.UserMapper.toEntity;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jmcurso.cource.dto.OrderResponseDTO;
import com.jmcurso.cource.dto.UserDTO;
import com.jmcurso.cource.dto.UserOrderListWithTotalDTO;
import com.jmcurso.cource.dto.UserPasswordUpdatedResponseDTO;
import com.jmcurso.cource.entities.Order;
import com.jmcurso.cource.entities.User;
import com.jmcurso.cource.mappers.UserMapper;
import com.jmcurso.cource.repositories.OrderRepository;
import com.jmcurso.cource.repositories.UserRepository;
import com.jmcurso.cource.services.exceptions.DatabaseException;
import com.jmcurso.cource.services.exceptions.InvalidPasswordException;
import com.jmcurso.cource.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public List<UserDTO> findAll() {
		List<User> list = userRepository.findAll();
		return list.stream().map(UserMapper::toDTO).collect(Collectors.toList());
	}

	public UserDTO findById(Long id) {
		User entity = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		return toDTO(entity);
	}

	public List<Order> findUserOrders(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(userId));
		return user.getOrders();
	}

	public UserDTO insert(UserDTO dto) {
		User entity = toEntity(dto);
		entity = userRepository.save(entity);
		return toDTO(entity);
	}

	public void delete(Long id) {
		if (!userRepository.existsById(id)) {
			throw new ResourceNotFoundException(id);
		}
		try {
			userRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	public UserDTO update(Long id, UserDTO dto) {
		try {
			User entity = userRepository.getReferenceById(id);
			updateData(entity, dto);
			User updatedUser = userRepository.save(entity);
			return toDTO(updatedUser);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	private void updateData(User entity, UserDTO dto) {
		entity.setName(dto.getName());
		entity.setEmail(dto.getEmail());
		entity.setPhone(dto.getPhone());
	}

	public UserPasswordUpdatedResponseDTO updatePassword(Long id, String oldPassword, String newPassword) {
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));

		if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
			throw new InvalidPasswordException("Sua senha atual não confere.");
		}

		if (passwordEncoder.matches(newPassword, user.getPassword())) {
			throw new InvalidPasswordException("A nova senha não pode ser igual à anterior.");
		}

		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);

		return new UserPasswordUpdatedResponseDTO(user.getId(), "Senha atualizada com sucesso!!!");
	}

	public UserOrderListWithTotalDTO findOrdersWithTotal(Long userId) {
		userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(userId));

		List<Order> list = orderRepository.findByClientId(userId);
		List<OrderResponseDTO> dtoList = list.stream().map(OrderResponseDTO::new).toList();

		return new UserOrderListWithTotalDTO(dtoList);
	}
}
