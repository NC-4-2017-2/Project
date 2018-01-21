package com.netcracker.project.services;

public interface EmailService {

  void sendEmail(String emailAddressTo, String login, String password);

}
