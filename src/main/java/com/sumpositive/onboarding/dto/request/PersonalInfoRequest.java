
package com.sumpositive.onboarding.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record PersonalInfoRequest(

    @NotBlank(message = "Phone number is required")
    @Pattern(
        regexp = "^\\+?[0-9]{10,15}$",
        message = "Enter a valid phone number"
    )
    String phoneNumber,

    @NotBlank(message = "Address is required")
    @Size(max = 120)
    String address,

    @NotBlank(message = "City is required")
    @Size(max = 45)
    String city,

    @NotBlank(message = "State is required")
    @Size(max = 45)
    String state,

    @NotBlank(message = "Postal code is required")
    @Size(max = 15)
    String postalCode,

    @NotBlank(message = "Emergency contact name is required")
    @Size(max = 45)
    String emergencyContactName,

    @NotBlank(message = "Emergency contact phone is required")
    @Pattern(
        regexp = "^\\+?[0-9]{10,15}$",
        message = "Enter a valid emergency contact phone"
    )
    String emergencyContactPhone,

    @NotBlank(message = "Relationship is required")
    @Size(max = 50)
    String emergencyContactRelationship

) {}
