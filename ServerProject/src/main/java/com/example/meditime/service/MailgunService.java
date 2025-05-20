package com.example.meditime.service;

import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class MailgunService {

    private static final String API_KEY = "b9363fb3babcf84bd05ac8c90e17db66-e71583bb-7da86459";
    private static final String DOMAIN_NAME = "sandbox9a37ee5b5cd64d0c985d25a817c6c623.mailgun.org"; 

    public void sendEmail(String toEmail, String subject, String body) {
        try {
            String form = "from=" + URLEncoder.encode("MediTime <noreply@" + DOMAIN_NAME + ">", StandardCharsets.UTF_8)
                    + "&to=" + URLEncoder.encode(toEmail, StandardCharsets.UTF_8)
                    + "&subject=" + URLEncoder.encode(subject, StandardCharsets.UTF_8)
                    + "&text=" + URLEncoder.encode(body, StandardCharsets.UTF_8);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://api.mailgun.net/v3/" + DOMAIN_NAME + "/messages"))
                    .header("Authorization", "Basic " + Base64.getEncoder().encodeToString(("api:" + API_KEY).getBytes()))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(form))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Mailgun response: " + response.statusCode());
            System.out.println("Response body: " + response.body());
        } catch (Exception e) {
            System.err.println("Error sending email via Mailgun:");
            e.printStackTrace();
        }
    }

    public void sendVerificationEmail(String toEmail, String token) {
        String verificationLink = "https://dd5c-203-164-254-42.ngrok-free.app/mobile/verify?token=" + token;
        String subject = "Verify your MediTime Email";
        String body = "Hello,\n\nPlease verify your email by clicking the link below:\n" + verificationLink + "\n\nThank you!";
        sendEmail(toEmail, subject, body);
    }
}
