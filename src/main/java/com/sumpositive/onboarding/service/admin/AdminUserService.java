
package com.sumpositive.onboarding.service.admin;

import java.util.List;
import java.util.Locale;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sumpositive.onboarding.dto.response.admin.AdminUserResponse;
import com.sumpositive.onboarding.entity.User;
import com.sumpositive.onboarding.exception.BusinessException;
import com.sumpositive.onboarding.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class AdminUserService {

	private final UserRepository userRepository;

	public AdminUserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	// Get all registered users
	public List<AdminUserResponse> getAllUsers() {

		return userRepository.findAll().stream().map(this::mapToResponse).toList();
	}

	// Get user by ID
	public AdminUserResponse getUserById(Long id) {

		User user = userRepository.findById(id)
				.orElseThrow(() -> new BusinessException("User not found with ID: " + id, HttpStatus.NOT_FOUND));

		return mapToResponse(user);
	}

	// Search users by name or email
	public List<AdminUserResponse> searchUsers(String keyword) {

		if (keyword == null || keyword.isBlank()) {
			return getAllUsers();
		}

		String search = keyword.trim().toLowerCase(Locale.ROOT);

		return userRepository.findAll().stream()
				.filter(user -> user.getFullName().toLowerCase(Locale.ROOT).contains(search)
						|| user.getEmail().toLowerCase(Locale.ROOT).contains(search))
				.map(this::mapToResponse).toList();
	}

	// Convert entity into safe response DTO
	private AdminUserResponse mapToResponse(User user) {

		return new AdminUserResponse(user.getId(), user.getFullName(), user.getEmail(), user.getRole(),
				user.getCreatedAt());
	}
}
