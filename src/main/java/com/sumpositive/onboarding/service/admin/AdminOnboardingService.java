
package com.sumpositive.onboarding.service.admin;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sumpositive.onboarding.dto.request.admin.AdminApprovalRequest;
import com.sumpositive.onboarding.dto.request.admin.AdminRejectionRequest;
import com.sumpositive.onboarding.dto.response.admin.AdminOnboardingResponse;
import com.sumpositive.onboarding.entity.OnboardingProgress;
import com.sumpositive.onboarding.enums.OnboardingStatus;
import com.sumpositive.onboarding.exception.BusinessException;
import com.sumpositive.onboarding.repository.OnboardingProgressRepository;

@Service
@Transactional(readOnly = true)
public class AdminOnboardingService {

	private final OnboardingProgressRepository progressRepository;

	public AdminOnboardingService(OnboardingProgressRepository progressRepository) {
		this.progressRepository = progressRepository;
	}

	public List<AdminOnboardingResponse> getAllOnboarding() {
		return progressRepository.findAll().stream().map(this::mapToResponse).toList();
	}

	public AdminOnboardingResponse getOnboardingById(Long progressId) {
		return mapToResponse(findProgress(progressId));
	}

	@Transactional
	public AdminOnboardingResponse approveOnboarding(Long progressId, AdminApprovalRequest request) {

		OnboardingProgress progress = findProgress(progressId);
		validatePendingAndCompleted(progress);

		progress.setStatus(OnboardingStatus.APPROVED);
		progress.setReviewedAt(LocalDateTime.now());
		progress.setRejectionReason(null);
		progress.setReviewRemarks(request == null ? null : request.remarks());

		return mapToResponse(progressRepository.save(progress));
	}

	@Transactional
	public AdminOnboardingResponse rejectOnboarding(Long progressId, AdminRejectionRequest request) {

		OnboardingProgress progress = findProgress(progressId);
		validatePendingAndCompleted(progress);

		progress.setStatus(OnboardingStatus.REJECTED);
		progress.setRejectionReason(request.reason().trim());
		progress.setReviewRemarks(null);
		progress.setReviewedAt(LocalDateTime.now());

		return mapToResponse(progressRepository.save(progress));
	}

	private OnboardingProgress findProgress(Long progressId) {
		return progressRepository.findById(progressId).orElseThrow(
				() -> new BusinessException("Onboarding record not found: " + progressId, HttpStatus.NOT_FOUND));
	}

	private void validatePendingAndCompleted(OnboardingProgress progress) {

		if (!Boolean.TRUE.equals(progress.getCompleted())) {
			throw new BusinessException("Onboarding is not completed yet", HttpStatus.BAD_REQUEST);
		}

		if (progress.getStatus() != OnboardingStatus.PENDING) {
			throw new BusinessException("Only pending onboarding records can be reviewed", HttpStatus.BAD_REQUEST);
		}
	}

	private AdminOnboardingResponse mapToResponse(OnboardingProgress progress) {

		return new AdminOnboardingResponse(progress.getId(), progress.getUser().getId(),
				progress.getUser().getFullName(), progress.getUser().getEmail(), progress.getCompletionPercentage(),
				progress.getCompleted(), progress.getStatus(), progress.getRejectionReason(), progress.getCompletedAt(),
				progress.getReviewedAt());
	}
}
