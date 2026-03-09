package com.chitthi.chatbot.service;

import com.chitthi.chatbot.model.BusinessInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class BusinessStorageService {

    private static final String STORAGE_FILE = "business-info.json";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void saveBusinessInfo(BusinessInfo businessInfo) throws IOException {
        objectMapper.writeValue(new File(STORAGE_FILE), businessInfo);
    }

    public BusinessInfo loadBusinessInfo() throws IOException {
        File file = new File(STORAGE_FILE);
        if (file.exists()) {
            return objectMapper.readValue(file, BusinessInfo.class);
        }
        return null;
    }

    public boolean hasBusinessInfo() {
        return new File(STORAGE_FILE).exists();
    }
}