package com.netcracker.project.services.impl;


import java.security.SecureRandom;
import org.apache.commons.lang3.RandomStringUtils;

public class PasswordService {

  public String generatePassword() {
    char[] possibleCharacters = ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789").toCharArray();
    String password = RandomStringUtils.random( 8, 0, possibleCharacters.length - 1, false, false, possibleCharacters, new SecureRandom() );
    return password;
  }

}
