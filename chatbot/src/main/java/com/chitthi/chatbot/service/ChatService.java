package com.chitthi.chatbot.service;

import com.chitthi.chatbot.model.BusinessInfo;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ChatService {
    private final ChatLanguageModel chatModel;
    private final BusinessStorageService storageService;

    private Map<String, BusinessInfo> businessCache = new HashMap<>();

    public ChatService(BusinessStorageService storageService) {
        this.storageService = storageService;
        this.chatModel = OllamaChatModel.builder()
                .baseUrl("http://127.0.0.1:11434")
                .modelName("llama3.2")
                .temperature(0.7)
                .build();
    }

    public void saveBusinessInfo(String businessId, BusinessInfo businessInfo) throws IOException {
        businessInfo.setBusinessId(businessId);
        storageService.saveBusinessInfo(businessId, businessInfo);
        businessCache.put(businessId, businessInfo);
    }

    public BusinessInfo getBusinessInfo(String businessId) throws IOException {
        if (!businessCache.containsKey(businessId)) {
            BusinessInfo info = storageService.loadBusinessInfo(businessId);
            if (info != null) {
                businessCache.put(businessId, info);
            }
        }
        return businessCache.get(businessId);
    }

    public String chat(String businessId, String userMessage) throws IOException {
        BusinessInfo businessInfo = getBusinessInfo(businessId);
        if (businessInfo == null) {
            return "Business not found. Please select a valid business.";
        }
        String fullPrompt = businessInfo.toContextString() +
                "\n\nCustomer Question: " + userMessage;
        return chatModel.generate(fullPrompt);
    }
}
