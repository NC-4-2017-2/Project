package com.netcracker.project.services.impl;

import com.netcracker.project.model.SecurityUserDAO;
import com.netcracker.project.model.entity.SecurityUser;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class SecurityUserServiceImpl implements UserDetailsService {

  private SecurityUserDAO securityUserDAO;

  public SecurityUserServiceImpl(SecurityUserDAO securityUserDAO) {
    this.securityUserDAO = securityUserDAO;
  }

  @Override
  public UserDetails loadUserByUsername(String username)
      throws UsernameNotFoundException {
    SecurityUser securityUser = securityUserDAO.findByUsername(username);
    return new org.springframework.security.core.userdetails.User(
        securityUser.getUsername(),
        securityUser.getPassword(),
        AuthorityUtils.createAuthorityList(securityUser.getRole()));
  }
}
