<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        p {
            color: red;
        }
    </style>
    <title>UpdateUserPassword</title>
</head>
<body>
<form action="/user/updateUserPassword/${user.userId}"
      method="post">
    First name:<input type="text" name="firstName" value="${user.firstName}"
                     required disabled><br>
    Last name:<input type="text" name="lastName" value="${user.lastName}"
                     required disabled><br>
    Birth day:<input type="date" name="birthDay" value="${user.dateOfBirth}"
                   required disabled><br>
    Password:<input type="text" name="password" value="${user.password}"
                   required><br>
    <!-- <%@include file="../errors/errorMap.jsp" %>-->
    <input type="submit" value="Update">
</form>
</body>
</html>
