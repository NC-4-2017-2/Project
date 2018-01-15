package com.netcracker.project.services;

import org.springframework.mail.MailSender;


public interface EmailService {

//  void setMailSender(MailSender mailSender);

  void sendEmail(String emailAddressTo, String login, String password);

}
