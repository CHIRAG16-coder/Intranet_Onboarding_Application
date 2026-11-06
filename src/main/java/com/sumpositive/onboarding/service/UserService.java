
package com.sumpositive.onboarding.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sumpositive.onboarding.dto.response.UserResponse;
import com.sumpositive.onboarding.entity.User;
import com.sumpositive.onboarding.exception.ResourceNotFoundException;
import com.sumpositive.onboarding.repository.UserRepository;

@Service
public class UserService 
{
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Transactional(readOnly = true)
	public UserResponse getCurrentUser(Long userId) {

		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		return new UserResponse(user.getId(), user.getFullName(), user.getEmail());
	}
}
