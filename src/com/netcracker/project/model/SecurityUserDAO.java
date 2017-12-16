package com.netcracker.project.model;

import com.netcracker.project.model.entity.SecurityUser;

public interface SecurityUserDAO {
  SecurityUser findByUsername(String userName);

  String FIND_BY_USERNAME = "SELECT USER_LOGIN.VALUE AS USER_NAME, USER_PASSWORD.VALUE AS USER_PASSWORD, ROLE_VALUE.VALUE AS USER_ROLE " +
      "FROM OBJECTS USER_ID, " +
      "ATTRIBUTES USER_LOGIN, ATTRIBUTES USER_PASSWORD, ATTRIBUTES USER_ROLE, " +
      "LISTVALUE ROLE_VALUE " +
      "WHERE USER_LOGIN.ATTR_ID = 10 AND " +
      "USER_LOGIN.VALUE = ? AND " +
      "USER_ID.OBJECT_ID = USER_LOGIN.OBJECT_ID AND " +
      "USER_PASSWORD.ATTR_ID = 11 AND " +
      "USER_PASSWORD.OBJECT_ID = USER_ID.OBJECT_ID  AND " +
      "USER_ROLE.ATTR_ID = 12 AND " +
      "USER_ROLE.OBJECT_ID = USER_ID.OBJECT_ID AND " +
      "ROLE_VALUE.ATTR_ID = USER_ROLE.ATTR_ID AND " +
      "ROLE_VALUE.LIST_VALUE_ID = USER_ROLE.LIST_VALUE_ID";
}
