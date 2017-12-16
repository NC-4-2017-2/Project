package com.netcracker.project.services;

import org.springframework.mail.MailSender;


public interface EmailService {

  void setMailSender(MailSender mailSender);

  void sendEmail(String to, String login, String password);

}
