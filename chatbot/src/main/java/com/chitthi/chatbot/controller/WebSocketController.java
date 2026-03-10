package com.chitthi.chatbot.controller;

import com.chitthi.chatbot.service.ChatService;
import lombok.Data;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    private final ChatService chatService;

    public WebSocketController(ChatService chatService) {
        this.chatService = chatService;
    }

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public ChatMessage chat(ChatMessage message) {
        try {
            String response = chatService.chat(message.getBusinessId(), message.getContent());
            return new ChatMessage("bot", response, message.getBusinessId());
        } catch (Exception e) {
            return new ChatMessage("bot", "Error: " + e.getMessage(), message.getBusinessId());
        }
    }

    @Data
    public static class ChatMessage {
        private String sender;
        private String content;
        private String businessId;

        public ChatMessage() {}

        public ChatMessage(String sender, String content, String businessId) {
            this.sender = sender;
            this.content = content;
            this.businessId = businessId;
        }
    }
}