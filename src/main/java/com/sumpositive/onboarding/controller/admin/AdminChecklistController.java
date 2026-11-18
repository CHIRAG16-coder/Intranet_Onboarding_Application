
package com.sumpositive.onboarding.controller.admin;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sumpositive.onboarding.entity.JoiningChecklistItem;
import com.sumpositive.onboarding.service.admin.AdminChecklistService;

@RestController
@RequestMapping("/api/admin/checklist")
public class AdminChecklistController {

	private final AdminChecklistService checklistService;

	public AdminChecklistController(AdminChecklistService checklistService) {
		this.checklistService = checklistService;
	}

	// Get all employee checklists
	@GetMapping
	public ResponseEntity<List<JoiningChecklistItem>> getAllChecklists() {
		return ResponseEntity.ok(checklistService.getAllChecklists());
	}

	// Get checklist by employee ID
	@GetMapping("/{userId}")
	public ResponseEntity<List<JoiningChecklistItem>> getChecklistByUserId(@PathVariable Long userId) {
		return ResponseEntity.ok(checklistService.getChecklistByUserId(userId));
	}

	// Mark checklist item completed
	@PutMapping("/{id}/complete")
	public ResponseEntity<JoiningChecklistItem> completeChecklist(@PathVariable Long id) {
		return ResponseEntity.ok(checklistService.completeChecklist(id));
	}

	// Mark checklist item pending
	@PutMapping("/{id}/pending")
	public ResponseEntity<JoiningChecklistItem> markChecklistPending(@PathVariable Long id) {
		return ResponseEntity.ok(checklistService.markChecklistPending(id));
	}

	// Delete checklist item
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteChecklist(@PathVariable Long id) {
		checklistService.deleteChecklist(id);
		return ResponseEntity.noContent().build();
	}
}
