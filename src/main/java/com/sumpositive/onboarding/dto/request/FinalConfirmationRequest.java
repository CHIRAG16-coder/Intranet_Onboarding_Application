
package com.sumpositive.onboarding.dto.request;

import jakarta.validation.constraints.AssertTrue;

public record FinalConfirmationRequest(

    @AssertTrue(
        message = "You must confirm completion of onboarding"
    )
    boolean confirmed

) {}
