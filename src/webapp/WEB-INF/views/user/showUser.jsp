<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <style>
        p {
            color: red;
        }
    </style>
    <title>User</title>
</head>
<body>
<table border="0">
    <tr>
        <td>User Name: </td>
        <td>${user.firstName} ${user.lastName}</td>
    </tr>
    <tr>
        <td>Email: </td>
        <td>${user.email}</td>
    </tr>
    <tr>
        <td>Birth date: </td>
        <td>${user.dateOfBirth}</td>
    </tr>
    <tr>
        <td>Hire date: </td>
        <td>${user.hireDate}</td>
    </tr>
    <tr>
        <td>Phone Number: </td>
        <td>${user.phoneNumber}</td>
    </tr>
    <tr>
        <td>Job Title: </td>
        <td>${user.jobTitle}</td>
    </tr>
    <tr>
        <td>Login: </td>
        <td>${user.login}</td>
    </tr>
    <tr>
        <td> User's Role: </td>
        <td>${user.userRole}</td>
    </tr>
    <tr>
        <td>User status: </td>
        <td>${user.userStatus}</td>
    </tr>
    <tr>
        <td>User's status on project: </td>
        <td>${user.projectStatus}</td>
    </tr>
</table>
<!-- <%@include file="../errors/errorMap.jsp" %> -->

<!-- here's forms for actions -->
<!-- only if edit by logged user -->
<form action="/user/updateUserPhoneNumber">
    <button type="submit">Edit phone number</button>
</form>
</body>
</html>
