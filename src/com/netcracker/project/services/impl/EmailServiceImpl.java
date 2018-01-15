package com.netcracker.project.services.impl;

import com.netcracker.project.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ImportResource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
@ImportResource("classpath:Email-Service.xml")
public class EmailServiceImpl implements EmailService {

  private MailSender mailSender;

  public void setMailSender(MailSender mailSender) {
    this.mailSender = mailSender;
  }

  public void sendEmail(String emailAddressTo, String login, String password) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo("<" + emailAddressTo + ">");
    message.setFrom("<team.erp.4.2017@gmail.com>");
    message.setSubject("Welcome to ERP system!");
    message.setText("Your login: " + login + "\n" + "Your password: " + password);
    mailSender.send(message);
  }
}
