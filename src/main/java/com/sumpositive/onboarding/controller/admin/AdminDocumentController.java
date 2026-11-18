
package com.sumpositive.onboarding.controller.admin;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import com.sumpositive.onboarding.dto.request.admin.AdminRejectionRequest;
import com.sumpositive.onboarding.dto.response.admin.AdminDocumentResponse;
import com.sumpositive.onboarding.service.admin.AdminDocumentService;

@RestController
@RequestMapping("/api/admin/documents")
public class AdminDocumentController {

	private final AdminDocumentService documentService;

	public AdminDocumentController(AdminDocumentService documentService) {
		this.documentService = documentService;
	}

	// Get all documents
	@GetMapping
	public List<AdminDocumentResponse> getAllDocuments() {
		return documentService.getAllDocuments();
	}

	// Get documents by employee
	@GetMapping("/user/{userId}")
	public List<AdminDocumentResponse> getDocumentsByUser(@PathVariable Long userId) {
		return documentService.getDocumentsByUser(userId);
	}

	// Approve document
	@PutMapping("/{documentId}/approve")
	public AdminDocumentResponse approveDocument(@PathVariable Long documentId) {
		return documentService.approveDocument(documentId);
	}

	// Reject document
	@PutMapping("/{documentId}/reject")
	public AdminDocumentResponse rejectDocument(@PathVariable Long documentId,
			@Valid @RequestBody AdminRejectionRequest request) {
		return documentService.rejectDocument(documentId, request.reason());
	}
}
