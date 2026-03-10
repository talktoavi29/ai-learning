package com.chitthi.chatbot.controller;

import com.chitthi.chatbot.model.BusinessInfo;
import com.chitthi.chatbot.service.ChatService;
import com.chitthi.chatbot.service.BusinessStorageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ChatController {

    private final ChatService chatService;
    private final BusinessStorageService storageService;

    public ChatController(ChatService chatService, BusinessStorageService storageService) {
        this.chatService = chatService;
        this.storageService = storageService;
    }

    @PostMapping("/business/setup/{businessId}")
    public SetupResponse setupBusiness(
            @PathVariable String businessId,
            @RequestBody BusinessInfo businessInfo) {
        try {
            chatService.saveBusinessInfo(businessId, businessInfo);
            return new SetupResponse("Business information set successfully for " + businessInfo.getBusinessName());
        } catch (Exception e) {
            return new SetupResponse("Error saving business information: " + e.getMessage());
        }
    }

    @GetMapping("/business/list")
    public List<BusinessInfo> listBusinesses() {
        try {
            return storageService.getAllBusinesses();
        } catch (Exception e) {
            return List.of();
        }
    }

    @GetMapping("/business/{businessId}")
    public BusinessInfo getBusiness(@PathVariable String businessId) {
        try {
            return chatService.getBusinessInfo(businessId);
        } catch (Exception e) {
            return null;
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