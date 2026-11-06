
package com.sumpositive.onboarding.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sumpositive.onboarding.entity.PersonalInfo;

public interface PersonalInfoRepository extends JpaRepository<PersonalInfo, Long> {

	Optional<PersonalInfo> findByUserId(Long userId);
}
