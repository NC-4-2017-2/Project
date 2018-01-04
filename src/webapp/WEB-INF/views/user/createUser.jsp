<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<html>
<head>
    <style type="text/css">
        div.user{
            align: center;
            text-align: center;
        }
        p {
            color: red;
        }
    </style>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Create User</title>
</head>
<body>
<div class="user">
    <form action="/user/createUser" method="post" name="/user/createUser">
        <table border="0">
            <tr>
                <h2>CreateUser</h2></td>
            </tr>
            <tr>
                <td>First Name:</td>
                <td><input type="text" name="firstName" size="40"
                           value=${firstName} required placeholder="Employee's firstname"></td>
            </tr>
            <tr>
                <td>Last Name:</td>
                <td><input type="text" name="lastName" size="40"
                           value=${lastName} required placeholder="Employee's lastname"></td>
            </tr>
            <tr>
                <td>Email:</td>
                <td><input type="email" name="email" size="40"
                           value=${email} required placeholder="email@example.com"></td>
            </tr>
            <tr>
                <td>Date Of Birth (mm/dd/yyyy):</td>
                <td><input type="date" name="dateOfBirth" size="20"
                           value=${dateOfBirth} required></td>
            </tr>
            <tr>
                <td>Hire Date (mm/dd/yyyy):</td>
                <td><input type="date" name="hireDate" size="20"
                           value=${hireDate} required></td>
            </tr>
            <tr>
                <td>Phone Number:</td>
                <td><input type="text" name="phoneNumber" size="20"
                           value=${phoneNumber} required placeholder="Employee's phone number"></td>
            </tr>
            <tr>
                <td>Job Title:</td>
                <td>
                    <select name="jobTitle" required>
                        <option value="PROJECT_MANAGER">PROJECT_MANAGER</option>
                        <option value="LINE_MANAGER">LINE_MANAGER</option>
                        <option selected="selected" value="SOFTWARE_ENGINEER">SOFTWARE_ENGINEER</option>
                    </select>
            </tr>
            <tr>
                <td>Login:</td>
                <td><input type="text" name="login" size="25"
                           value=${login} required placeholder="Employee's login"></td>
            </tr>
            <tr>
                <td>Password:</td>
                <td><input type="password" name="password" size="25"
                           value=${password} required placeholder="Employee's password"></td>
            </tr>
            <tr>
                <td>User's Role:</td>
                <td>
                    <select name="userRole" required>
                        <option value="ROLE_ADMIN">ROLE_ADMIN</option>
                        <option value="ROLE_PM">ROLE_PM</option>
                        <option value="ROLE_LM">ROLE_LM</option>
                        <option selected="selected" value="ROLE_SE">ROLE_SE</option>
                    </select>
            </tr>
            <tr>
                <td><input type="submit" value="Create"/></td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>