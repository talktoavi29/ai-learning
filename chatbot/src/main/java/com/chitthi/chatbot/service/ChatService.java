package com.chitthi.chatbot.service;

import com.chitthi.chatbot.model.BusinessInfo;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ChatService {
    private final ChatLanguageModel chatModel;
    private final BusinessStorageService storageService;
    private BusinessInfo businessInfo;

    public ChatService(BusinessStorageService storageService) {
        this.storageService = storageService;
        this.chatModel = OllamaChatModel.builder()
                .baseUrl("http://127.0.0.1:11434")
                .modelName("llama3.2")
                .temperature(0.7)
                .build();

        try {
            this.businessInfo = storageService.loadBusinessInfo();
        } catch (IOException e) {
            System.out.println("No existing business info found");
        }
    }

    public void setBusinessInfo(BusinessInfo businessInfo) throws IOException {
        this.businessInfo = businessInfo;
        storageService.saveBusinessInfo(businessInfo);
    }

    public BusinessInfo getBusinessInfo() {
        return businessInfo;
    }

    public String chat(String userMessage) {
        if (businessInfo == null) {
            return "Please set up business information first.";
        }

        String fullPrompt = businessInfo.toContextString() +
                "\n\nCustomer Question: " + userMessage;

        return chatModel.generate(fullPrompt);
    }
}
