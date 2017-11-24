package main.net.cracker.project.services.impl;

import main.net.cracker.project.services.EmailService;
import org.springframework.mail.MailSender;


public class EmailServiceImpl implements EmailService {

  private MailSender mailSender;


  public void setMailSender(MailSender mailSender) {

  }


  public void sendEmail(String to, String login, String password) {

  }
}
