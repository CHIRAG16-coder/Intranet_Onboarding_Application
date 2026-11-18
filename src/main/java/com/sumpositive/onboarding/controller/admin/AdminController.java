
package com.sumpositive.onboarding.controller.admin;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sumpositive.onboarding.dto.response.admin.AdminUserResponse;
import com.sumpositive.onboarding.service.admin.AdminUserService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	private final AdminUserService adminUserService;

	public AdminController(AdminUserService adminUserService) {
		this.adminUserService = adminUserService;
	}

	// Get all employees
	@GetMapping("/users")
	public List<AdminUserResponse> getAllUsers() {
		return adminUserService.getAllUsers();
	}

	// Get employee by ID
	@GetMapping("/users/{id}")
	public AdminUserResponse getUserById(@PathVariable Long id) {
		return adminUserService.getUserById(id);
	}

	// Search employees
	@GetMapping("/users/search")
	public List<AdminUserResponse> searchUsers(@RequestParam String keyword) {
		return adminUserService.searchUsers(keyword);
	}
}
