
package com.sumpositive.onboarding.dto.request.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AdminRejectionRequest(

        @NotBlank(message = "Rejection reason is required")
        @Size(max = 500, message = "Reason cannot exceed 500 characters")
        String reason

) {
}
