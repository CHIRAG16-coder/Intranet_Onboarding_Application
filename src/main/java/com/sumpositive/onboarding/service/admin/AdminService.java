
package com.sumpositive.onboarding.service.admin;

import java.util.Locale;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.sumpositive.onboarding.dto.request.admin.AdminLoginRequest;
import com.sumpositive.onboarding.dto.response.AuthResponse;
import com.sumpositive.onboarding.entity.User;
import com.sumpositive.onboarding.enums.Role;
import com.sumpositive.onboarding.exception.BusinessException;
import com.sumpositive.onboarding.repository.UserRepository;
import com.sumpositive.onboarding.security.JwtService;
import com.sumpositive.onboarding.dto.response.UserResponse;

@Service
public class AdminService {

	private final UserRepository userRepository;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;

	public AdminService(UserRepository userRepository, AuthenticationManager authenticationManager,
			JwtService jwtService) {

		this.userRepository = userRepository;
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
	}

	public AuthResponse adminLogin(AdminLoginRequest request) {

		String email = request.email().trim().toLowerCase(Locale.ROOT);

		User admin = userRepository.findByEmail(email)
				.orElseThrow(() -> new BusinessException("Invalid email or password", HttpStatus.UNAUTHORIZED));

		if (admin.getRole() != Role.ADMIN) {
			throw new BusinessException("Access denied. Admin account required.", HttpStatus.FORBIDDEN);
		}

		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, request.password()));

		String token = jwtService.generateToken(admin);

		UserResponse userResponse = new UserResponse(admin.getId(), admin.getFullName(), admin.getEmail());

		return new AuthResponse(token, "Bearer", jwtService.getExpiration() / 1000, userResponse);
	}
}
