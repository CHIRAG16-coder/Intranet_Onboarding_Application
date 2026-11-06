
package com.sumpositive.onboarding.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sumpositive.onboarding.dto.request.PersonalInfoRequest;
import com.sumpositive.onboarding.dto.response.ProfileResponse;
import com.sumpositive.onboarding.entity.User;
import com.sumpositive.onboarding.repository.UserRepository;
import com.sumpositive.onboarding.service.OnboardingService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/onboarding/personal-info")
@SecurityRequirement(name = "bearerAuth")
public class PersonalInfoController {

	private final OnboardingService onboardingService;
	private final UserRepository userRepository;

	public PersonalInfoController(OnboardingService onboardingService, UserRepository userRepository) {
		this.onboardingService = onboardingService;
		this.userRepository = userRepository;
	}

	private Long getAuthenticatedUserId(Authentication authentication) {
		User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
		return user.getId();
	}

	// SAVE PERSONAL INFORMATION
	@PostMapping
	public ProfileResponse savePersonalInfo(Authentication authentication,
			@Valid @RequestBody PersonalInfoRequest request) {

		Long userId = getAuthenticatedUserId(authentication);

		return onboardingService.savePersonalInfo(userId, request);
	}

	// GET PERSONAL INFORMATION
	@GetMapping
	public ProfileResponse getPersonalInfo(Authentication authentication) {

		Long userId = getAuthenticatedUserId(authentication);

		return onboardingService.getProfile(userId);
	}
}
