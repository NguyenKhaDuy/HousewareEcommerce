package com.example.housewareecommerce.Service;

public interface EmailService {
    void sendEmail(String to, String subject, String content) throws Exception;
}
