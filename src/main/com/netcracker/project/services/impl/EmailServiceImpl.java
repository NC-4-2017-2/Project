package main.com.netcracker.project.services.impl;

import main.com.netcracker.project.services.EmailService;
import org.springframework.mail.MailSender;


public class EmailServiceImpl implements EmailService {

  private MailSender mailSender;


  public void setMailSender(MailSender mailSender) {

  }


  public void sendEmail(String to, String login, String password) {

  }
}
