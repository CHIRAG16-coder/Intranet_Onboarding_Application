
package com.sumpositive.onboarding.dto.response.admin;

public record AdminDashboardResponse(

        long totalEmployees,

        long completedOnboarding,

        long pendingOnboarding,

        double averageCompletionPercentage

) {
}
