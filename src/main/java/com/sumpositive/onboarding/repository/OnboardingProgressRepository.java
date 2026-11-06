
package com.sumpositive.onboarding.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sumpositive.onboarding.entity.OnboardingProgress;

public interface OnboardingProgressRepository extends JpaRepository<OnboardingProgress, Long> {

	Optional<OnboardingProgress> findByUserId(Long userId);
}
