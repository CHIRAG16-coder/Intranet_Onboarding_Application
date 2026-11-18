
package com.sumpositive.onboarding.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "admin_audit_logs")
@NoArgsConstructor
@AllArgsConstructor
public class AdminAuditLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "admin_id", nullable = false)
	private User admin;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "target_user_id", nullable = false)
	private User targetUser;

	@Column(nullable = false, length = 20)
	private String action;

	@Column(length = 255)
	private String description;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	@PrePersist
	public void onCreate() {
		if (this.createdAt == null) {
			this.createdAt = LocalDateTime.now();
		}
	}
}
