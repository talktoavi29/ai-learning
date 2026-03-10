package com.chitthi.chatbot.service;

import com.chitthi.chatbot.model.BusinessInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BusinessStorageService {

    private static final String STORAGE_DIR = "businesses/";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public BusinessStorageService() {
        new File(STORAGE_DIR).mkdirs();
    }

    public void saveBusinessInfo(String businessId, BusinessInfo businessInfo) throws IOException {
        String filename = STORAGE_DIR + businessId + ".json";
        objectMapper.writeValue(new File(filename), businessInfo);
    }

    public BusinessInfo loadBusinessInfo(String businessId) throws IOException {
        String filename = STORAGE_DIR + businessId + ".json";
        File file = new File(filename);
        if (file.exists()) {
            return objectMapper.readValue(file, BusinessInfo.class);
        }
        return null;
    }

    public List<String> listBusinesses() {
        List<String> businesses = new ArrayList<>();
        File dir = new File(STORAGE_DIR);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".json"));

        if (files != null) {
            for (File file : files) {
                String businessId = file.getName().replace(".json", "");
                businesses.add(businessId);
            }
        }
        return businesses;
    }

    public List<BusinessInfo> getAllBusinesses() throws IOException {
        List<BusinessInfo> businesses = new ArrayList<>();
        for (String businessId : listBusinesses()) {
            businesses.add(loadBusinessInfo(businessId));
        }
        return businesses;
    }
}