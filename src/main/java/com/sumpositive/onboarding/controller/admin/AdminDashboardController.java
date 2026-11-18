
package com.sumpositive.onboarding.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sumpositive.onboarding.dto.response.admin.AdminDashboardResponse;
import com.sumpositive.onboarding.service.admin.AdminDashboardService;

@RestController
@RequestMapping("/api/admin/dashboard")
public class AdminDashboardController {

	private final AdminDashboardService dashboardService;

	public AdminDashboardController(AdminDashboardService dashboardService) {

		this.dashboardService = dashboardService;
	}

	@GetMapping
	public ResponseEntity<AdminDashboardResponse> getDashboard() {

		return ResponseEntity.ok(dashboardService.getDashboard());
	}
}
