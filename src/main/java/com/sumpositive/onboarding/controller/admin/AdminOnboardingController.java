
package com.sumpositive.onboarding.controller.admin;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.sumpositive.onboarding.dto.request.admin.AdminApprovalRequest;
import com.sumpositive.onboarding.dto.request.admin.AdminRejectionRequest;
import com.sumpositive.onboarding.dto.response.admin.AdminOnboardingResponse;
import com.sumpositive.onboarding.service.admin.AdminOnboardingService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/onboarding")
@SecurityRequirement(name = "bearerAuth")
public class AdminOnboardingController {

	private final AdminOnboardingService adminOnboardingService;

	public AdminOnboardingController(AdminOnboardingService adminOnboardingService) {
		this.adminOnboardingService = adminOnboardingService;
	}

	// Get all onboarding records
	@GetMapping
	public List<AdminOnboardingResponse> getAllOnboarding() {
		return adminOnboardingService.getAllOnboarding();
	}

	// Get onboarding by ID
	@GetMapping("/{progressId}")
	public AdminOnboardingResponse getOnboardingById(@PathVariable Long progressId) {
		return adminOnboardingService.getOnboardingById(progressId);
	}

	// Approve onboarding
	@PatchMapping("/{progressId}/approve")
	public AdminOnboardingResponse approveOnboarding(@PathVariable Long progressId,
			@Valid @RequestBody AdminApprovalRequest request) {
		return adminOnboardingService.approveOnboarding(progressId, request);
	}

	// Reject onboarding
	@PatchMapping("/{progressId}/reject")
	public AdminOnboardingResponse rejectOnboarding(@PathVariable Long progressId,
			@Valid @RequestBody AdminRejectionRequest request) {
		return adminOnboardingService.rejectOnboarding(progressId, request);
	}
}
