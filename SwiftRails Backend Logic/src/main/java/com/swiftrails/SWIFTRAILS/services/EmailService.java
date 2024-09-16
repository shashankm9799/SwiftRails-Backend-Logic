package com.swiftrails.SWIFTRAILS.services;

import java.util.concurrent.CompletableFuture;

public interface EmailService {
	public CompletableFuture<Void> sendEmail(String to, String subject, String text) throws Exception;
    public String getOtpLoginEmailTemplate(String name, String otp) ; 
}
