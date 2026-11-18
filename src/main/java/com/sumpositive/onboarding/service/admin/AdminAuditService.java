
package com.sumpositive.onboarding.service.admin;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sumpositive.onboarding.dto.response.admin.AdminAuditResponse;
import com.sumpositive.onboarding.entity.AdminAuditLog;
import com.sumpositive.onboarding.repository.AdminAuditLogRepository;

@Service
public class AdminAuditService {

	private final AdminAuditLogRepository auditRepository;

	public AdminAuditService(AdminAuditLogRepository auditRepository) {
		this.auditRepository = auditRepository;
	}

	@Transactional(readOnly = true)
	public List<AdminAuditResponse> getAllAuditLogs() {

		return auditRepository.findAll().stream().map(this::convertToResponse).toList();
	}

	private AdminAuditResponse convertToResponse(AdminAuditLog log) {

		return new AdminAuditResponse(log.getId(), log.getAdmin().getId(), log.getAdmin().getFullName(),
				log.getTargetUser().getId(), log.getTargetUser().getFullName(), log.getAction(), log.getDescription(),
				log.getCreatedAt());
	}
}
