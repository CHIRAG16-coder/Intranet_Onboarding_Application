
package com.sumpositive.onboarding.dto.request;

import jakarta.validation.constraints.AssertTrue;

public record AcknowledgementRequest(

    @AssertTrue(message = "You must acknowledge this step")
    boolean acknowledged

) {}
