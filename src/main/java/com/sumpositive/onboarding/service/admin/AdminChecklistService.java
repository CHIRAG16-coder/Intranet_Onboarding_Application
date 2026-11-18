
package com.sumpositive.onboarding.service.admin;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sumpositive.onboarding.entity.JoiningChecklistItem;
import com.sumpositive.onboarding.exception.BusinessException;
import com.sumpositive.onboarding.repository.JoiningChecklistRepository;

@Service
@Transactional(readOnly = true)
public class AdminChecklistService {

	private final JoiningChecklistRepository checklistRepository;

	public AdminChecklistService(JoiningChecklistRepository checklistRepository) {
		this.checklistRepository = checklistRepository;
	}

	// Get all employee checklist records
	public List<JoiningChecklistItem> getAllChecklists() {
		return checklistRepository.findAll();
	}

	// Get checklist for a particular employee
	public List<JoiningChecklistItem> getChecklistByUserId(Long userId) {
		return checklistRepository.findAllByUserId(userId);
	}

	// Mark checklist item as completed
	@Transactional
	public JoiningChecklistItem completeChecklist(Long id) {

		JoiningChecklistItem item = findChecklist(id);

		item.setCompleted(true);
		item.setCompletedAt(LocalDateTime.now());

		return checklistRepository.save(item);
	}

	// Mark checklist item as pending
	@Transactional
	public JoiningChecklistItem markChecklistPending(Long id) {

		JoiningChecklistItem item = findChecklist(id);

		item.setCompleted(false);
		item.setCompletedAt(null);

		return checklistRepository.save(item);
	}

	// Delete checklist item
	@Transactional
	public void deleteChecklist(Long id) {

		JoiningChecklistItem item = findChecklist(id);

		checklistRepository.delete(item);
	}

	private JoiningChecklistItem findChecklist(Long id) {

		return checklistRepository.findById(id)
				.orElseThrow(() -> new BusinessException("Checklist item not found: " + id, HttpStatus.NOT_FOUND));
	}
}
