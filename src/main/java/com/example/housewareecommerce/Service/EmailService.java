package com.example.housewareecommerce.Service.Impl;

public interface EmailService {
    void sendEmail(String to, String subject, String content) throws Exception;
}
