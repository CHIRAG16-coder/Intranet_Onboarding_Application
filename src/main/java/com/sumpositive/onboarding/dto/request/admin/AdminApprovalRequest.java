
package com.sumpositive.onboarding.dto.request.admin;

import jakarta.validation.constraints.Size;

public record AdminApprovalRequest(

        @Size(max = 500, message = "Remarks cannot exceed 500 characters")
        String remarks

) {
}
