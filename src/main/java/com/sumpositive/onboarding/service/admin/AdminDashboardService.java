
package com.sumpositive.onboarding.service.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sumpositive.onboarding.dto.response.admin.AdminDashboardResponse;
import com.sumpositive.onboarding.entity.OnboardingProgress;
import com.sumpositive.onboarding.entity.User;
import com.sumpositive.onboarding.enums.Role;
import com.sumpositive.onboarding.repository.OnboardingProgressRepository;
import com.sumpositive.onboarding.repository.UserRepository;

@Service
public class AdminDashboardService {

	private final UserRepository userRepository;
	private final OnboardingProgressRepository progressRepository;

	public AdminDashboardService(UserRepository userRepository, OnboardingProgressRepository progressRepository) {

		this.userRepository = userRepository;
		this.progressRepository = progressRepository;
	}

	@Transactional(readOnly = true)
	public AdminDashboardResponse getDashboard() {

		// Get all employees (exclude administrators)
		List<User> employees = userRepository.findAll().stream().filter(user -> user.getRole() == Role.USER).toList();

		// Get onboarding progress records
		List<OnboardingProgress> progressList = progressRepository.findAll();

		Map<Long, OnboardingProgress> progressMap = new HashMap<>();

		for (OnboardingProgress progress : progressList) {
			if (progress.getUser() != null && progress.getUser().getRole() == Role.USER) {

				progressMap.put(progress.getUser().getId(), progress);
			}
		}

		long totalEmployees = employees.size();
		long completed = 0;
		double totalPercentage = 0;

		for (User employee : employees) {

			OnboardingProgress progress = progressMap.get(employee.getId());

			if (progress != null) {

				if (Boolean.TRUE.equals(progress.getCompleted())) {
					completed++;
				}

				if (progress.getCompletionPercentage() != null) {
					totalPercentage += progress.getCompletionPercentage();
				}
			}
		}

		long pending = totalEmployees - completed;

		double averagePercentage = totalEmployees == 0 ? 0 : totalPercentage / totalEmployees;

		averagePercentage = Math.round(averagePercentage * 100.0) / 100.0;

		return new AdminDashboardResponse(totalEmployees, completed, pending, averagePercentage);
	}
}
