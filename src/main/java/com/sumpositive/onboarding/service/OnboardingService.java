
package com.sumpositive.onboarding.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sumpositive.onboarding.dto.request.PersonalInfoRequest;
import com.sumpositive.onboarding.dto.response.ChecklistResponse;
import com.sumpositive.onboarding.dto.response.ProfileResponse;
import com.sumpositive.onboarding.dto.response.ProgressResponse;
import com.sumpositive.onboarding.dto.response.StepResponse;

import com.sumpositive.onboarding.entity.JoiningChecklistItem;
import com.sumpositive.onboarding.entity.OnboardingProgress;
import com.sumpositive.onboarding.entity.OnboardingStep;
import com.sumpositive.onboarding.entity.PersonalInfo;
import com.sumpositive.onboarding.entity.User;

import com.sumpositive.onboarding.enums.ChecklistType;
import com.sumpositive.onboarding.enums.StepType;

import com.sumpositive.onboarding.exception.BusinessException;
import com.sumpositive.onboarding.exception.ResourceNotFoundException;

import com.sumpositive.onboarding.repository.JoiningChecklistRepository;
import com.sumpositive.onboarding.repository.OnboardingProgressRepository;
import com.sumpositive.onboarding.repository.OnboardingStepRepository;
import com.sumpositive.onboarding.repository.PersonalInfoRepository;
import com.sumpositive.onboarding.repository.UserRepository;

@Service
public class OnboardingService {

	private static final int TOTAL_STEPS = 5;

	private final UserRepository userRepository;
	private final PersonalInfoRepository personalInfoRepository;
	private final OnboardingProgressRepository progressRepository;
	private final OnboardingStepRepository stepRepository;
	private final JoiningChecklistRepository checklistRepository;

	public OnboardingService(UserRepository userRepository, PersonalInfoRepository personalInfoRepository,
			OnboardingProgressRepository progressRepository, OnboardingStepRepository stepRepository,
			JoiningChecklistRepository checklistRepository) {

		this.userRepository = userRepository;
		this.personalInfoRepository = personalInfoRepository;
		this.progressRepository = progressRepository;
		this.stepRepository = stepRepository;
		this.checklistRepository = checklistRepository;
	}

	// GET USER

	private User getUser(Long userId) {

		return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
	}

	// SAVE PERSONAL INFORMATION

	@Transactional
	public ProfileResponse savePersonalInfo(Long userId, PersonalInfoRequest request) {

		User user = getUser(userId);

		PersonalInfo info = personalInfoRepository.findByUserId(userId).orElseGet(PersonalInfo::new);

		info.setUser(user);
		info.setPhoneNumber(request.phoneNumber());
		info.setAddress(request.address());
		info.setCity(request.city());
		info.setState(request.state());
		info.setPostalCode(request.postalCode());

		info.setEmergencyContactName(request.emergencyContactName());
		info.setEmergencyContactPhone(request.emergencyContactPhone());
		info.setEmergencyContactRelationship(request.emergencyContactRelationship());

		personalInfoRepository.save(info);

		completeStep(userId, StepType.PERSONAL_INFORMATION);

		return new ProfileResponse(user.getId(), user.getFullName(), user.getEmail(), info.getPhoneNumber(),
				info.getAddress(), info.getCity(), info.getState(), info.getPostalCode(),
				info.getEmergencyContactName(), info.getEmergencyContactPhone(),
				info.getEmergencyContactRelationship());
	}

	// GET PERSONAL INFORMATION

	@Transactional(readOnly = true)
	public ProfileResponse getProfile(Long userId) {

		User user = getUser(userId);

		PersonalInfo info = personalInfoRepository.findByUserId(userId).orElse(null);

		return new ProfileResponse(user.getId(), user.getFullName(), user.getEmail(),
				info == null ? null : info.getPhoneNumber(), info == null ? null : info.getAddress(),
				info == null ? null : info.getCity(), info == null ? null : info.getState(),
				info == null ? null : info.getPostalCode(), info == null ? null : info.getEmergencyContactName(),
				info == null ? null : info.getEmergencyContactPhone(),
				info == null ? null : info.getEmergencyContactRelationship());
	}

	// COMPLETE INTRODUCTION OR COMPANY POLICIES

	@Transactional
	public void acknowledgeStep(Long userId, StepType stepType) {

		if (stepType != StepType.INTRODUCTION && stepType != StepType.COMPANY_POLICIES) {

			throw new BusinessException("This step cannot be acknowledged using this endpoint", HttpStatus.BAD_REQUEST);
		}

		OnboardingStep step = stepRepository.findByUserIdAndStepType(userId, stepType)
				.orElseThrow(() -> new ResourceNotFoundException("Onboarding step not found"));

		if (Boolean.TRUE.equals(step.getCompleted())) {
			return;
		}

		// Company policies require Introduction completion.

		if (stepType == StepType.COMPANY_POLICIES) {

			ensureStepCompleted(userId, StepType.INTRODUCTION);
		}

		step.setCompleted(true);
		step.setCompletedAt(LocalDateTime.now());

		stepRepository.save(step);

		recalculateProgress(userId);
	}

	// COMPLETE ONBOARDING STEP

	private void completeStep(Long userId, StepType type) {

		OnboardingStep step = stepRepository.findByUserIdAndStepType(userId, type)
				.orElseThrow(() -> new ResourceNotFoundException("Onboarding step not found"));

		if (!Boolean.TRUE.equals(step.getCompleted())) {

			step.setCompleted(true);
			step.setCompletedAt(LocalDateTime.now());

			stepRepository.save(step);
		}

		recalculateProgress(userId);
	}

	// VERIFY PREVIOUS STEP

	private void ensureStepCompleted(Long userId, StepType type) {

		OnboardingStep step = stepRepository.findByUserIdAndStepType(userId, type)
				.orElseThrow(() -> new ResourceNotFoundException("Onboarding step not found"));

		if (!Boolean.TRUE.equals(step.getCompleted())) {

			throw new BusinessException("Complete " + type + " before proceeding", HttpStatus.CONFLICT);
		}
	}

	// GET CHECKLIST

	@Transactional(readOnly = true)
	public List<ChecklistResponse> getChecklist(Long userId) {

		getUser(userId);

		return checklistRepository.findAllByUserId(userId).stream()
				.map(item -> new ChecklistResponse(item.getItemType(), item.getCompleted(), item.getCompletedAt()))
				.toList();
	}

	// UPDATE CHECKLIST

	@Transactional
	public ChecklistResponse updateChecklist(Long userId, ChecklistType itemType, boolean completed) {

		OnboardingStep checklistStep = stepRepository.findByUserIdAndStepType(userId, StepType.JOINING_CHECKLIST)
				.orElseThrow(() -> new ResourceNotFoundException("Checklist onboarding step not found"));

		if (completed) {

			ensureStepCompleted(userId, StepType.COMPANY_POLICIES);
		}

		JoiningChecklistItem item = checklistRepository.findByUserIdAndItemType(userId, itemType)
				.orElseThrow(() -> new ResourceNotFoundException("Checklist item not found"));

		item.setCompleted(completed);

		item.setCompletedAt(completed ? LocalDateTime.now() : null);

		checklistRepository.save(item);

		List<JoiningChecklistItem> items = checklistRepository.findAllByUserId(userId);

		boolean allCompleted = items.size() == 3
				&& items.stream().allMatch(checklistItem -> Boolean.TRUE.equals(checklistItem.getCompleted()));

		if (allCompleted) {

			checklistStep.setCompleted(true);

			if (checklistStep.getCompletedAt() == null) {
				checklistStep.setCompletedAt(LocalDateTime.now());
			}

		} else {

			checklistStep.setCompleted(false);
			checklistStep.setCompletedAt(null);
		}

		stepRepository.save(checklistStep);

		recalculateProgress(userId);

		return new ChecklistResponse(item.getItemType(), item.getCompleted(), item.getCompletedAt());
	}

	// FINAL CONFIRMATION

	@Transactional
	public ProgressResponse completeOnboarding(Long userId) {

		OnboardingProgress progress = progressRepository.findByUserId(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Onboarding progress not found"));

		// All previous steps must be completed.

		for (StepType type : StepType.values()) {

			if (type == StepType.FINAL_CONFIRMATION) {
				continue;
			}

			ensureStepCompleted(userId, type);
		}

		OnboardingStep finalStep = stepRepository.findByUserIdAndStepType(userId, StepType.FINAL_CONFIRMATION)
				.orElseThrow(() -> new ResourceNotFoundException("Final confirmation step not found"));

		finalStep.setCompleted(true);
		finalStep.setCompletedAt(LocalDateTime.now());

		stepRepository.save(finalStep);

		progress.setCompleted(true);
		progress.setCompletionPercentage(100);
		progress.setCompletedAt(LocalDateTime.now());

		progressRepository.save(progress);

		return getProgress(userId);
	}

	// RECALCULATE ONBOARDING PROGRESS

	@Transactional
	public ProgressResponse recalculateProgress(Long userId) {

		List<OnboardingStep> steps = stepRepository.findAllByUserIdOrderByStepType(userId);

		OnboardingProgress progress = progressRepository.findByUserId(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Onboarding progress not found"));

		long completedCount = steps.stream().filter(step -> Boolean.TRUE.equals(step.getCompleted())).count();

		int percentage = (int) (completedCount * 100 / TOTAL_STEPS);

		// Final confirmation controls 100% completion.

		if (!Boolean.TRUE.equals(progress.getCompleted())) {

			percentage = Math.min(percentage, 80);

		} else {

			percentage = 100;
		}

		progress.setCompletionPercentage(percentage);

		progressRepository.save(progress);

		return getProgress(userId);
	}

	// GET ONBOARDING PROGRESS

	@Transactional(readOnly = true)
	public ProgressResponse getProgress(Long userId) {

		OnboardingProgress progress = progressRepository.findByUserId(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Onboarding progress not found"));

		List<OnboardingStep> steps = stepRepository.findAllByUserIdOrderByStepType(userId);

		List<StepResponse> responses = steps.stream().map(step -> new StepResponse(step.getStepType(),
				Boolean.TRUE.equals(step.getCompleted()), step.getCompletedAt())).toList();

		int completedCount = (int) steps.stream().filter(step -> Boolean.TRUE.equals(step.getCompleted())).count();

		// Calculate percentage using current database records.

		int percentage = (int) (completedCount * 100 / TOTAL_STEPS);

		if (Boolean.TRUE.equals(progress.getCompleted())) {
			percentage = 100;
		} else {
			percentage = Math.min(percentage, 80);
		}

		return new ProgressResponse(TOTAL_STEPS, completedCount, percentage,
				Boolean.TRUE.equals(progress.getCompleted()), responses);
	}

	// GET ALL ONBOARDING STEPS

	@Transactional(readOnly = true)
	public List<StepResponse> getSteps(Long userId) {

		getUser(userId);

		return stepRepository.findAllByUserIdOrderByStepType(userId).stream()
				.map(step -> new StepResponse(step.getStepType(), Boolean.TRUE.equals(step.getCompleted()),
						step.getCompletedAt()))
				.toList();
	}
}
