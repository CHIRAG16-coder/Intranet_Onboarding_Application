
package com.sumpositive.onboarding.entity;

import java.time.LocalDateTime;

import com.sumpositive.onboarding.enums.ChecklistType;

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
@Table(name = "joining_checklist", uniqueConstraints = {
		@UniqueConstraint(name = "uk_user_checklist", columnNames = { "user_id", "item_type" }) })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JoiningChecklistItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Enumerated(EnumType.STRING)
	@Column(name = "item_type", nullable = false, length = 40)
	private ChecklistType itemType;

	@Column(name = "is_completed", nullable = false)
	private Boolean completed = false;

	@Column(name = "completed_at")
	private LocalDateTime completedAt;
}
