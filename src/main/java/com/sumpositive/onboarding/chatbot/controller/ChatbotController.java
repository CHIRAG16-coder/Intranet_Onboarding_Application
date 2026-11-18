
package com.sumpositive.onboarding.chatbot.controller;

import com.sumpositive.onboarding.chatbot.dto.ChatRequest;
import com.sumpositive.onboarding.chatbot.dto.ChatResponse;
import com.sumpositive.onboarding.chatbot.service.OpenAIService;

import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/chatbot")
@CrossOrigin(origins = "http://localhost:5173")

// Add these two annotations here
@Tag(name = "Chatbot", description = "Employee onboarding chatbot")
@SecurityRequirement(name = "bearerAuth")

public class ChatbotController {

	private final OpenAIService openAIService;

	public ChatbotController(OpenAIService openAIService) {
		this.openAIService = openAIService;
	}

	@PostMapping("/chat")
	public ChatResponse chat(@RequestBody ChatRequest request) {

		if (request.message() == null || request.message().isBlank()) {
			throw new IllegalArgumentException("Message cannot be empty");
		}

		String reply = openAIService.generateResponse(request.message());

		return new ChatResponse(reply);
	}
}
