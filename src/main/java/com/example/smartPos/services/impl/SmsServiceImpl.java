package com.example.smartPos.services.impl;

import com.example.smartPos.services.ISmsService;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;
import com.twilio.Twilio;

@Service
public class SmsServiceImpl implements ISmsService {

    // Find your Account SID and Auth Token at twilio.com/console
    // and set the environment variables. See http://twil.io/secure
    public static final String ACCOUNT_SID = "ACa47a9477ccb9f84d16f5ab8d70e2af9d";
    public static final String AUTH_TOKEN = "a7e21a428a895cdd94fc67ce073c3d5e";
    @Override
    public void sendSms(String phoneNumber, String message) {

         Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
         Message.creator(new PhoneNumber(phoneNumber), new PhoneNumber("+15645294861"), message).create();


        System.out.println("Sending SMS to " + phoneNumber + ": " + message);
    }
}
