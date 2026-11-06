
package com.sumpositive.onboarding.entity;

import java.time.LocalDateTime;

import com.sumpositive.onboarding.enums.StepType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "onboarding_steps", uniqueConstraints = {
		@UniqueConstraint(name = "uk_user_step", columnNames = { "user_id", "step_type" }) })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OnboardingStep {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Enumerated(EnumType.STRING)
	@Column(name = "step_type", nullable = false, length = 40)
	private StepType stepType;

	@Column(name = "is_completed", nullable = false)
	private Boolean completed = false;

	@Column(name = "completed_at")
	private LocalDateTime completedAt;
}
