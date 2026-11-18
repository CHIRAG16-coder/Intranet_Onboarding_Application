
package com.sumpositive.onboarding.dto.request.admin;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AdminLoginRequest(

    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email")
    String email,

    @NotBlank(message = "Password is required")
    String password

) {}
