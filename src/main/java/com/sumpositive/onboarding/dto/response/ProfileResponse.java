
package com.sumpositive.onboarding.dto.response;

public record ProfileResponse(

    Long userId,

    String fullName,

    String email,

    String phoneNumber,

    String address,

    String city,

    String state,

    String postalCode,

    String emergencyContactName,

    String emergencyContactPhone,

    String emergencyContactRelationship

) {}
