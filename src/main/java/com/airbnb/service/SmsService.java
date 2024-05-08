package com.airbnb.service;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    @Value("${twilio.accountSid}")
    private String ACCOUNT_SID;

    @Value("${twilio.authToken}")
    private String AUTH_TOKEN;

    @Value("${twilio.phoneNumber}")
    private String FROM_NUMBER;

    public void sendSms(String to, String message) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message.creator(
                        new PhoneNumber(to),
                        new PhoneNumber(FROM_NUMBER),
                        message)
                .create();
    }
}
