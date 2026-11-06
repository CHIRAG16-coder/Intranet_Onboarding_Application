
package com.sumpositive.onboarding.dto.response;

public record AuthResponse(

    String accessToken,

    String tokenType,

    long expiresIn,

    UserResponse user

) {}
