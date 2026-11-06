
package com.sumpositive.onboarding.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sumpositive.onboarding.dto.request.AcknowledgementRequest;
import com.sumpositive.onboarding.dto.request.FinalConfirmationRequest;
import com.sumpositive.onboarding.dto.response.ChecklistResponse;
import com.sumpositive.onboarding.dto.response.ProgressResponse;
import com.sumpositive.onboarding.dto.response.StepResponse;
import com.sumpositive.onboarding.entity.User;
import com.sumpositive.onboarding.enums.ChecklistType;
import com.sumpositive.onboarding.enums.StepType;
import com.sumpositive.onboarding.repository.UserRepository;
import com.sumpositive.onboarding.service.OnboardingService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/onboarding")
@SecurityRequirement(name = "bearerAuth")
public class OnboardingController {

	private final OnboardingService onboardingService;

	private final UserRepository userRepository;

	public OnboardingController(OnboardingService onboardingService, UserRepository userRepository) {

		this.onboardingService = onboardingService;
		this.userRepository = userRepository;
	}

	private Long getAuthenticatedUserId(Authentication authentication) {
		User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
		return user.getId();
	}

	// GET ALL ONBOARDING STEPS

	@GetMapping("/steps")
	public List<StepResponse> getSteps(Authentication authentication) {
		Long userId = getAuthenticatedUserId(authentication);
		return onboardingService.getSteps(userId);
	}

	// GET ONBOARDING PROGRESS

	@GetMapping("/progress")
	public ProgressResponse getProgress(Authentication authentication) {
		Long userId = getAuthenticatedUserId(authentication);
		return onboardingService.getProgress(userId);
	}

	// ACKNOWLEDGE INTRODUCTION OR POLICIES

	@PutMapping("/steps/{stepType}")
	public ProgressResponse acknowledgeStep(Authentication authentication, @PathVariable StepType stepType,
			@Valid @RequestBody AcknowledgementRequest request) {
		Long userId = getAuthenticatedUserId(authentication);
		onboardingService.acknowledgeStep(userId, stepType);
		return onboardingService.getProgress(userId);
	}

	// GET CHECKLIST

	@GetMapping("/checklist")
	public List<ChecklistResponse> getChecklist(Authentication authentication) {
		Long userId = getAuthenticatedUserId(authentication);
		return onboardingService.getChecklist(userId);
	}

	// UPDATE CHECKLIST

	@PatchMapping("/checklist/{itemType}")
	public ChecklistResponse updateChecklist(Authentication authentication, @PathVariable ChecklistType itemType,
			@RequestParam boolean completed) {
		Long userId = getAuthenticatedUserId(authentication);
		return onboardingService.updateChecklist(userId, itemType, completed);
	}

	// FINAL CONFIRMATION

	@PostMapping("/complete")
	public ProgressResponse completeOnboarding(Authentication authentication,
			@Valid @RequestBody FinalConfirmationRequest request) {
		Long userId = getAuthenticatedUserId(authentication);
		return onboardingService.completeOnboarding(userId);
	}
}
