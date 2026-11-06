
package com.sumpositive.onboarding.service;

import com.sumpositive.onboarding.dto.request.RegisterRequest;
import com.sumpositive.onboarding.dto.request.LoginRequest;

import com.sumpositive.onboarding.dto.response.AuthResponse;
import com.sumpositive.onboarding.dto.response.UserResponse;

import com.sumpositive.onboarding.entity.*;

import com.sumpositive.onboarding.enums.ChecklistType;
import com.sumpositive.onboarding.enums.StepType;

import com.sumpositive.onboarding.exception.BusinessException;

import com.sumpositive.onboarding.repository.*;

import com.sumpositive.onboarding.security.JwtService;

import org.springframework.http.HttpStatus;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
public class AuthService {

	private final UserRepository userRepository;

	private final OnboardingProgressRepository progressRepository;

	private final OnboardingStepRepository stepRepository;

	private final JoiningChecklistRepository checklistRepository;

	private final PasswordEncoder passwordEncoder;

	private final AuthenticationManager authenticationManager;

	private final JwtService jwtService;

	public AuthService(UserRepository userRepository, OnboardingProgressRepository progressRepository,
			OnboardingStepRepository stepRepository, JoiningChecklistRepository checklistRepository,
			PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {

		this.userRepository = userRepository;
		this.progressRepository = progressRepository;
		this.stepRepository = stepRepository;
		this.checklistRepository = checklistRepository;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
	}

	
	@Transactional
	public AuthResponse register(RegisterRequest request) {
		String email = request.email().trim().toLowerCase(Locale.ROOT);
		if (userRepository.existsByEmail(email)) {
			throw new BusinessException("An account with this email already exists", HttpStatus.CONFLICT);
		}

		User user = new User();
		user.setFullName(request.fullName().trim());
		user.setEmail(email);
		user.setPassword(passwordEncoder.encode(request.password()));
		userRepository.saveAndFlush(user);

		// Create initial progress.

		OnboardingProgress progress = new OnboardingProgress();
		progress.setUser(user);
		progress.setCompletionPercentage(0);
		progress.setCompleted(false);
		progressRepository.save(progress);

		// Create all five onboarding steps.

		for (StepType type : StepType.values()) {
			OnboardingStep step = new OnboardingStep();
			step.setUser(user);
			step.setStepType(type);
			step.setCompleted(false);
			stepRepository.save(step);
		}

		// Create the three joining checklist items.

		for (ChecklistType type : ChecklistType.values()) {
			JoiningChecklistItem item = new JoiningChecklistItem();
			item.setUser(user);
			item.setItemType(type);
			item.setCompleted(false);
			checklistRepository.save(item);
		}
		return createAuthResponse(user);
	}

	
	public AuthResponse login(LoginRequest request) {
		String email = request.email().trim().toLowerCase(Locale.ROOT);
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, request.password()));
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new BusinessException("Invalid email or password", HttpStatus.UNAUTHORIZED));
		return createAuthResponse(user);
	}

	
	
	private AuthResponse createAuthResponse(User user) {
		String token = jwtService.generateToken(user.getEmail());
		UserResponse userResponse = new UserResponse(user.getId(), user.getFullName(), user.getEmail());
		return new AuthResponse(token, "Bearer", jwtService.getExpiration() / 1000, userResponse);
	}
}
