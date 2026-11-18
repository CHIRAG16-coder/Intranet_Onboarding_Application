
package com.sumpositive.onboarding.controller.admin;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sumpositive.onboarding.dto.response.admin.AdminAuditResponse;
import com.sumpositive.onboarding.service.admin.AdminAuditService;

@RestController
@RequestMapping("/api/admin/audit")
public class AdminAuditController {

    private final AdminAuditService auditService;

    public AdminAuditController(AdminAuditService auditService) {
        this.auditService = auditService;
    }

    @GetMapping
    public List<AdminAuditResponse> getAllAuditLogs() {
        return auditService.getAllAuditLogs();
    }
}
