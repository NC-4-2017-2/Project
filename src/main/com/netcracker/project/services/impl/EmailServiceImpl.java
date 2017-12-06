package main.com.netcracker.project.services.impl;

import main.com.netcracker.project.services.EmailService;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

  private MailSender mailSender;

  public void setMailSender(MailSender mailSender) {
    this.mailSender = mailSender;
  }

  public void sendEmail(String to, String login, String password) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(to);
    message.setSubject(login);
    message.setText(password);
    mailSender.send(message);
  }
}
