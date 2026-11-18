
package com.sumpositive.onboarding.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sumpositive.onboarding.entity.AdminAuditLog;

@Repository
public interface AdminAuditLogRepository extends JpaRepository<AdminAuditLog, Long> {

}
