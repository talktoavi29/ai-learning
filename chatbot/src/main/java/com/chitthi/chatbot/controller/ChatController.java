package com.chitthi.chatbot.controller;

import com.chitthi.chatbot.model.BusinessInfo;
import com.chitthi.chatbot.service.ChatService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/business/setup")
    public SetupResponse setupBusiness(@RequestBody BusinessInfo businessInfo) {
        try {
            chatService.setBusinessInfo(businessInfo);
            return new SetupResponse("Business information set successfully for " + businessInfo.getBusinessName());
        } catch (IOException e)
        {
            return new SetupResponse("Error saving business info: "+ e.getMessage());
        }
    }

    @PostMapping("/chat/message")
    public ChatResponse sendMessage(@RequestBody ChatRequest request) {
        String response = chatService.chat(request.getMessage());
        return new ChatResponse(response);
    }

    public static class ChatRequest {
        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public static class ChatResponse {
        private String response;

        public ChatResponse(String response) {
            this.response = response;
        }

        public String getResponse() {
            return response;
        }

        public void setResponse(String response) {
            this.response = response;
        }
    }

    public static class SetupResponse {
        private String message;

        public SetupResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}