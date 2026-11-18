
package com.sumpositive.onboarding.controller.admin;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.sumpositive.onboarding.dto.response.admin.AdminUserResponse;
import com.sumpositive.onboarding.service.admin.AdminUserService;

@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {

	private final AdminUserService adminUserService;

	public AdminUserController(AdminUserService adminUserService) {
		this.adminUserService = adminUserService;
	}

	// Get all users
	@GetMapping
	public List<AdminUserResponse> getAllUsers() {
		return adminUserService.getAllUsers();
	}

	// Get user by ID
	@GetMapping("/{id}")
	public AdminUserResponse getUserById(@PathVariable Long id) {
		return adminUserService.getUserById(id);
	}

	// Search users
	@GetMapping("/search")
	public List<AdminUserResponse> searchUsers(@RequestParam String keyword) {
		return adminUserService.searchUsers(keyword);
	}
}
