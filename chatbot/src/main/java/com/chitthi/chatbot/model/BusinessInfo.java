package com.chitthi.chatbot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessInfo {
    private String businessName;
    private String businessType;
    private String operatingHours;
    private String location;
    private String returnPolicy;
    private String deliveryInfo;
    private String contactInfo;
    private String additionalInfo;

    public String toContextString() {
        StringBuilder context = new StringBuilder();
        context.append("You are a customer service assistant for ").append(businessName).append(".\n");
        context.append("Business Type: ").append(businessType).append("\n");
        context.append("Operating Hours: ").append(operatingHours).append("\n");
        context.append("Location: ").append(location).append("\n");
        context.append("Return Policy: ").append(returnPolicy).append("\n");
        context.append("Delivery Information: ").append(deliveryInfo).append("\n");
        context.append("Contact: ").append(contactInfo).append("\n");
        if (additionalInfo != null && !additionalInfo.isEmpty()) {
            context.append("Additional Info: ").append(additionalInfo).append("\n");
        }
        context.append("\nAnswer customer questions based on this information. Be friendly and helpful.");
        return context.toString();
    }
}


