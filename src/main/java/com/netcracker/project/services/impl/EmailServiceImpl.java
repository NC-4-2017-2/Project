package com.netcracker.project.services.impl;

import com.netcracker.project.services.EmailService;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.ImportResource;
import org.springframework.mail.MailSendException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
@ImportResource("classpath:Email-Service.xml")
public class EmailServiceImpl implements EmailService {

  private MailSender mailSender;
  private static final Logger logger = Logger.getLogger(EmailServiceImpl.class);
  public void setMailSender(MailSender mailSender) {
    this.mailSender = mailSender;
  }

  public void sendEmail(String emailAddressTo, String login, String password) {
    try {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo("<" + emailAddressTo + ">");
    message.setFrom("<team.erp.4.2017@outlook.com>");
    message.setSubject("Welcome to ERP system!");
    message.setText("Your login: " + login + "\n" + "Your password: " + password);
    mailSender.send(message);
    } catch (MailSendException exception) {
      logger.error("sendEmail exception", exception);
    }
  }
}
