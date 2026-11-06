
package com.sumpositive.onboarding.dto.response;

import java.util.List;

public record ProgressResponse(

    int totalSteps,

    int completedSteps,

    int completionPercentage,

    boolean completed,

    List<StepResponse> steps

) {}
