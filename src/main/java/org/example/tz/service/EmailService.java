package org.example.tz.service;



import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendRegistrationEmail(String to, String courseName, String startDate) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Course Registration Confirmation");
        message.setText("You are registered for the course: " + courseName +
                "\nStart Date: " + startDate);

        try {
            System.out.println("Sending email to: " + to);
            System.out.println("Subject: " + message.getSubject());
            System.out.println("Body:\n" + message.getText());

            mailSender.send(message);
        } catch (Exception e) {
            System.err.println(" Email send FAILED:");
            e.printStackTrace();
        }
    }

}
