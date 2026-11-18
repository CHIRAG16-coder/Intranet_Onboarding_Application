
package com.sumpositive.onboarding.dto.response.admin;

import java.time.LocalDateTime;

import com.sumpositive.onboarding.enums.OnboardingStatus;

public record AdminOnboardingResponse(
        Long progressId,
        Long userId,
        String fullName,
        String email,
        Integer completionPercentage,
        Boolean completed,
        OnboardingStatus status,
        String rejectionReason,
        LocalDateTime completedAt,
        LocalDateTime reviewedAt
) {
}
