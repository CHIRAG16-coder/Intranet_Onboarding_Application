package com.sumpositive.onboarding.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sumpositive.onboarding.dto.request.PersonalInfoRequest;
import com.sumpositive.onboarding.dto.response.ProfileResponse;
import com.sumpositive.onboarding.dto.response.UserResponse;
import com.sumpositive.onboarding.entity.User;
import com.sumpositive.onboarding.repository.UserRepository;
import com.sumpositive.onboarding.service.OnboardingService;
import com.sumpositive.onboarding.service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api")
public class ProfileControlller {

	private final UserService userService;

	private final OnboardingService onboardingService;

	private final UserRepository userRepository;

	public ProfileControlller(UserService userService, OnboardingService onboardingService,
			UserRepository userRepository) {
		super();
		this.userService = userService;
		this.onboardingService = onboardingService;
		this.userRepository = userRepository;
	}

	public Long getAuthenticatedUserId(Authentication authentication) {
		User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
		return user.getId();
	}

	@GetMapping("/users/me")
	public UserResponse getCurrentUser(Authentication authentication) {
		Long userId = getAuthenticatedUserId(authentication);
		return userService.getCurrentUser(userId);
	}

	@GetMapping("/profile")
	public ProfileResponse getProfile(Authentication authentication) {
		Long userId = getAuthenticatedUserId(authentication);
		return onboardingService.getProfile(userId);
	}

	@PostMapping("/profile")
	public ProfileResponse saveProfile(Authentication authentication, @Valid @RequestBody PersonalInfoRequest request) {
		Long userId = getAuthenticatedUserId(authentication);
		return onboardingService.savePersonalInfo(userId, request);
	}

}
