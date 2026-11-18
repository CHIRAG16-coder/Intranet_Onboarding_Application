
package com.sumpositive.onboarding.dto.response.admin;

import java.time.LocalDateTime;

public record AdminDocumentResponse(
        Long id,
        Long userId,
        String employeeName,
        String documentName,
        String documentType,
        String status,
        String rejectionReason,
        LocalDateTime uploadedAt
) {
}
