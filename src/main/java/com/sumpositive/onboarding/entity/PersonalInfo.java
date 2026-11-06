
package com.sumpositive.onboarding.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "personal_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonalInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false, unique = true)
	private User user;

	@Column(name = "phone_number", length = 20)
	private String phoneNumber;

	@Column(length = 120)
	private String address;

	@Column(name = "city", length = 45)
	private String city;

	@Column(name = "state", length = 45)
	private String state;

	@Column(name = "postal_code", length = 15)
	private String postalCode;

	@Column(name = "emergency_contact_name", length = 45)
	private String emergencyContactName;

	@Column(name = "emergency_contact_phone", length = 20)
	private String emergencyContactPhone;

	@Column(name = "emergency_contact_relationship", length = 50)
	private String emergencyContactRelationship;
}
