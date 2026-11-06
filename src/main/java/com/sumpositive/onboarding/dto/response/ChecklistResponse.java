
package com.sumpositive.onboarding.dto.response;

import com.sumpositive.onboarding.enums.ChecklistType;

import java.time.LocalDateTime;

public record ChecklistResponse(

    ChecklistType itemType,

    boolean completed,

    LocalDateTime completedAt

) {}
