
package com.sumpositive.onboarding.dto.response.admin;

import java.time.LocalDateTime;

import com.sumpositive.onboarding.enums.Role;

public record AdminUserResponse(
        Long id,
        String fullName,
        String email,
        Role role,
        LocalDateTime createdAt
) {
}
