
package com.sumpositive.onboarding.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sumpositive.onboarding.entity.OnboardingStep;
import com.sumpositive.onboarding.enums.StepType;

public interface OnboardingStepRepository extends JpaRepository<OnboardingStep, Long> {

	List<OnboardingStep> findAllByUserIdOrderByStepType(Long userId);

	Optional<OnboardingStep> findByUserIdAndStepType(Long userId, StepType stepType);
}
