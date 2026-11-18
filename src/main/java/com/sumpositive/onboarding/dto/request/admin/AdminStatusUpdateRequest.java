
package com.sumpositive.onboarding.dto.request.admin;

import jakarta.validation.constraints.NotNull;

import com.sumpositive.onboarding.enums.OnboardingStatus;

public record AdminStatusUpdateRequest(

    @NotNull(message = "Status is required")
    OnboardingStatus status

) {}
