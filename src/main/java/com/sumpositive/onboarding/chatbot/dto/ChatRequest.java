package com.sumpositive.onboarding.chatbot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChatRequest(
	@NotBlank(message = "Message cannot be empty")
	@Size(max = 2000, message = "Message is too long")
	String message
	) 
{}
