package com.aiken.pos.admin.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * @author Sajith Alahakoon
 * @version aiken-admin-portal / 1.0
 * @since 2024-December-23
 */
@Configuration
public class EmailConfiguration {
    private static Logger logger = LoggerFactory.getLogger(EmailConfiguration.class);

    @Value("${aiken.email.enable}")
    private boolean isEmailEnable;

    @Value("${aiken.email.host}")
    private String host;

    @Value("${aiken.email.port}")
    private int port;

    @Value("${aiken.email.username}")
    private String username;

    @Value("${aiken.email.password}")
    private String password;

    @Value("${aiken.email.protocol}")
    private String protocol;

    @Value("${aiken.email.default-encoding}")
    private String defaultEncode;

    @Value("${aiken.email.properties.mail.smtp.auth}")
    private String smtpAuthEnable;

    @Value("${aiken.email.properties.mail.smtp.starttls.enable}")
    private String starttlsEnable;

    /**
     * build java mail sender bean object
     */
    @Bean(name = "aikenMailSender")
    public JavaMailSender buildJavaMailSender() {
        if (isEmailEnable) {
            try {
                JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
                javaMailSender.setHost(host);
                javaMailSender.setPort(port);
                javaMailSender.setUsername(username);
                javaMailSender.setPassword(password);
                javaMailSender.setProtocol(protocol);
                javaMailSender.setDefaultEncoding(defaultEncode);

                Properties properties = new Properties();
                properties.setProperty("mail.smtp.auth", smtpAuthEnable);
                properties.setProperty("mail.smtp.starttls.enable", starttlsEnable);

                javaMailSender.setJavaMailProperties(properties);
                return javaMailSender;
            } catch (Exception e) {
                logger.error("Fail to build java mail sender : {}" , e.getMessage());
            }
        }

        return null;
    }
}
