package com.example.smartPos.services.impl;
import com.example.smartPos.services.ISmsService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class SmsServiceImpl implements ISmsService {
    private static final String API_URL = "https://app.text.lk/api/http/sms/send";
    public static final String API_TOKEN = "1785|UTswRgPLVQI6jgJr1EpFbCcriVkxbHEmSPScPBTL0853b06f";
    @Override
    public void sendSms(String phoneNumber, String message) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Accept", "application/json");


         // Remove '+' from the phone number
        String sanitizedPhoneNumber = phoneNumber.replace("+", "");
        // Create request body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("api_token", API_TOKEN);
        requestBody.put("recipient", sanitizedPhoneNumber);
        requestBody.put("sender_id", "TRUE_ENTE");
        requestBody.put("type", "plain");
        requestBody.put("message", message);

        // Create HTTP entity
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        // Initialize RestTemplate
        RestTemplate restTemplate = new RestTemplate();
        // Make POST request
        ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, requestEntity, String.class);

        // Log response
        System.out.println("Response: " + response.getBody());

    }
}
