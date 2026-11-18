
package com.sumpositive.onboarding.chatbot.service;

import org.springframework.stereotype.Service;

@Service
public class OpenAIService {

    public String generateResponse(String message) {

        if (message == null || message.isBlank()) {
            return "Please enter your question.";
        }

        String question = message.toLowerCase().trim();

        // Greeting
        if (question.contains("hello")
                || question.contains("hi")
                || question.contains("hey")) {

            return "Hello! Welcome to SUMPOSITIVE. "
                    + "How can I help you with your onboarding?";
        }

        // Onboarding steps
        if (question.contains("onboarding")
                || question.contains("steps")) {

            return "Your onboarding process includes:\n"
                    + "1. Personal information\n"
                    + "2. Company introduction\n"
                    + "3. Company policies\n"
                    + "4. Joining checklist\n"
                    + "5. Final confirmation.";
        }

        // Documents
        if (question.contains("document")
                || question.contains("documents")) {

            return "Please contact HR for the verified "
                    + "list of documents required for joining.";
        }

        // Company policies
        if (question.contains("policy")
                || question.contains("policies")) {

            return "For company policies, please check "
                    + "the employee handbook or contact HR.";
        }

        // Joining checklist
        if (question.contains("checklist")
                || question.contains("joining")) {

            return "Your joining checklist helps you complete "
                    + "all required onboarding activities. "
                    + "Please open the Joining Checklist section "
                    + "in your employee portal.";
        }

        // Help
        if (question.contains("help")) {

            return "I can help you with:\n"
                    + "1. Onboarding steps\n"
                    + "2. Required documents\n"
                    + "3. Company policies\n"
                    + "4. Joining checklist\n"
                    + "5. General onboarding questions.";
        }

        // Default response
        return "Sorry, I don't have an answer for that yet. "
                + "Please contact the HR department for assistance.";
    }
}
