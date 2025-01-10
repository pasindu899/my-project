package com.aiken.pos.admin.util.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author Sajith Alahakoon
 * @version aiken-admin-portal / 1.0
 * @since 2024-December-23
 */
@Component
public class SendEmailAdapter {
    private static Logger logger = LoggerFactory.getLogger(SendEmailAdapter.class);
    private final JavaMailSender mailSender;
    @Value("${aiken.email.enable}")
    private boolean isEmailEnable;

    @Value("${aiken.email.subject}")
    private String subject;

    @Value("${aiken.email.title}")
    private String title;

    @Value("${aiken.email.username}")
    private String fromEmail;

    public SendEmailAdapter(@Qualifier(value = "aikenMailSender") JavaMailSender javaMailSender) {
        this.mailSender = javaMailSender;
    }

    public void sendEmail(String sendEmail, String username, String password) {
        if (sendEmail == null || sendEmail.trim().isEmpty()) {
            logger.info("send email address should not be null or empty");
            throw new IllegalArgumentException("send email address should not be null or empty");
        }
        if (isEmailEnable && mailSender != null) {

            try {
                MimeMessage msg = mailSender.createMimeMessage();
                msg.setFrom(fromEmail);
                InternetAddress[] toAddresses = {new InternetAddress(sendEmail)};
                msg.setRecipients(Message.RecipientType.TO, toAddresses);
                msg.setSubject(subject);
                //set msg body
                String msgBody = "<html>" + "<center><h2>" + title + "</h2></center>" +
                        "<center><h3>" + "Your login details are as follows:" + "</h3></center>" +
                        "<center><h3>" + "Username : " + username + "</h3></center>" +
                        "<center><h3>" + "Temporary Password : " + password + "</h3></center>" +
                        "</html>";

                msg.setContent(msgBody, "text/html");

                //send msg to the user
                mailSender.send(msg);
                logger.info("email send successfully");

            } catch (MessagingException e) {
                logger.error("fail to send email some error occur: {}", e.getMessage());
            }

        } else {
            logger.info("#### Email function is disable or java mail sender not available ####");
        }
    }
}
