
package com.sumpositive.onboarding.service.admin;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sumpositive.onboarding.dto.response.admin.AdminDocumentResponse;
import com.sumpositive.onboarding.entity.Document;
import com.sumpositive.onboarding.exception.BusinessException;
import com.sumpositive.onboarding.exception.ResourceNotFoundException;
import com.sumpositive.onboarding.repository.DocumentRepository;

@Service
public class AdminDocumentService {

	private final DocumentRepository documentRepository;

	public AdminDocumentService(DocumentRepository documentRepository) {
		this.documentRepository = documentRepository;
	}

	// Get all employee documents
	@Transactional(readOnly = true)
	public List<AdminDocumentResponse> getAllDocuments() {

		return documentRepository.findAll().stream().map(this::convertToResponse).toList();
	}

	// Get documents by employee ID
	@Transactional(readOnly = true)
	public List<AdminDocumentResponse> getDocumentsByUser(Long userId) {

		return documentRepository.findByUserId(userId).stream().map(this::convertToResponse).toList();
	}

	// Convert entity to response DTO
	private AdminDocumentResponse convertToResponse(Document document) {

		return new AdminDocumentResponse(document.getId(), document.getUser().getId(), document.getUser().getFullName(),
				document.getDocumentName(), document.getDocumentType(), document.getStatus(),
				document.getRejectionReason(), document.getUploadedAt());
	}

	// Approve employee document
	@Transactional
	public AdminDocumentResponse approveDocument(Long documentId) {

		Document document = documentRepository.findById(documentId)
				.orElseThrow(() -> new ResourceNotFoundException("Document not found"));

		if (!"PENDING".equals(document.getStatus())) {
			throw new BusinessException("Only pending documents can be processed");
		}

		document.setStatus("APPROVED");
		document.setRejectionReason(null);

		return convertToResponse(documentRepository.save(document));
	}

	// Reject employee document
	@Transactional
	public AdminDocumentResponse rejectDocument(Long documentId, String reason) {

		Document document = documentRepository.findById(documentId)
				.orElseThrow(() -> new ResourceNotFoundException("Document not found"));

		if (!"PENDING".equals(document.getStatus())) {
			throw new BusinessException("Only pending documents can be processed");
		}

		if (reason == null || reason.isBlank()) {
			throw new BusinessException("Rejection reason is required");
		}

		document.setStatus("REJECTED");
		document.setRejectionReason(reason);

		return convertToResponse(documentRepository.save(document));
	}
}
