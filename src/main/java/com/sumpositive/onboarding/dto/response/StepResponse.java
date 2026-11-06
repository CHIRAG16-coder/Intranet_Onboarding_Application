
package com.sumpositive.onboarding.dto.response;

import com.sumpositive.onboarding.enums.StepType;

import java.time.LocalDateTime;

public record StepResponse(

    StepType stepType,

    boolean completed,

    LocalDateTime completedAt

) {}
