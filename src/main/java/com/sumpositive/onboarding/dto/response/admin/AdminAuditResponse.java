
package com.sumpositive.onboarding.dto.response.admin;

import java.time.LocalDateTime;

public record AdminAuditResponse(
    Long id,
    Long adminId,
    String adminName,
    Long targetUserId,
    String targetUserName,
    String action,
    String description,
    LocalDateTime createdAt
) {}
