
package com.sumpositive.onboarding.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sumpositive.onboarding.entity.JoiningChecklistItem;
import com.sumpositive.onboarding.enums.ChecklistType;

public interface JoiningChecklistRepository extends JpaRepository<JoiningChecklistItem, Long> {

	List<JoiningChecklistItem> findAllByUserId(Long userId);

	Optional<JoiningChecklistItem> findByUserIdAndItemType(Long userId, ChecklistType itemType);
}
