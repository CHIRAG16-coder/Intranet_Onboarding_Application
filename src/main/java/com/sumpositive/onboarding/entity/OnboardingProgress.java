
package com.sumpositive.onboarding.entity;

import java.time.LocalDateTime;

import com.sumpositive.onboarding.enums.OnboardingStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "onboarding_progress")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OnboardingProgress {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false, unique = true)
	private User user;

	@Column(name = "completion_percentage", nullable = false)
	private Integer completionPercentage = 0;

	@Column(name = "is_completed", nullable = false)
	private Boolean completed = false;

	@Column(name = "completed_at")
	private LocalDateTime completedAt;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, length = 20)
	private OnboardingStatus status = OnboardingStatus.PENDING;

	@Column(name = "rejection_reason", length = 500)
	private String rejectionReason;
	
	@Column(name = "review_remarks", length = 500)
	private String reviewRemarks;

	@Column(name = "reviewed_at")
	private LocalDateTime reviewedAt;

	@PrePersist
	public void onCreate() {
		if (this.status == null) {
			this.status = OnboardingStatus.PENDING;
		}
	}
}
