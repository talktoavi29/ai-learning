package com.chitthi.chatbot.service;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    private final ChatLanguageModel chatModel;

    public ChatService() {
        this.chatModel = OllamaChatModel.builder()
                .baseUrl("http://127.0.0.1:11434")
                .modelName("llama3.2")
                .temperature(0.7)
                .build();
    }

    public String chat(String userMessage){
        return chatModel.generate(userMessage);
    }
}
