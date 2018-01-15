<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Create user</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery-2.1.4.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<jsp:include page="../fragments/header.jsp"></jsp:include>
<form action="/user/createUser" method="post" name="/user/createUser">
    <div class="col-lg-6">
        <table border="0">
            <tr>
                <h2>Create user</h2></td>
            </tr>
            <tr>
                <td>Last Name:</td>
                <td><input type="text" name="lastName" size="40"
                           required placeholder="Employee's last name"></td>
            </tr>
            <tr>
                <td>First Name:</td>
                <td><input type="text" name="firstName" size="40"
                           required placeholder="Employee's first name"></td>
            </tr>
            <tr>
                <td>Email:</td>
                <td><input type="email" name="email" size="40"
                           required placeholder="email@example.com"></td>
            </tr>
            <tr>
                <td>Date Of Birth (mm/dd/yyyy):</td>
                <td><input type="date" name="dateOfBirth" size="20"
                           required></td>
            </tr>
            <tr>
                <td>Hire Date (mm/dd/yyyy):</td>
                <td><input type="date" name="hireDate" size="20"
                           required></td>
            </tr>
            <tr>
                <td>Phone Number:</td>
                <td><input type="text" name="phoneNumber" size="20"
                           required placeholder="Employee's phone number"></td>
            </tr>
            <tr>
                <td>Job Title:</td>
                <td>
                    <select name="jobTitle" required>
                        <option value="PROJECT_MANAGER">PROJECT_MANAGER</option>
                        <option value="LINE_MANAGER">LINE_MANAGER</option>
                        <option selected="selected" value="SOFTWARE_ENGINEER">
                            SOFTWARE_ENGINEER
                        </option>
                    </select>
            </tr>
            <tr>
                <td>Login:</td>
                <td><input type="text" name="login" size="25"
                           required placeholder="Employee's login"></td>
            </tr>
            <tr>
                <td>User's Role:</td>
                <td>
                    <select name="userRole" required>
                        <option value="ROLE_ADMIN">ROLE_ADMIN</option>
                        <option value="ROLE_PM">ROLE_PM</option>
                        <option value="ROLE_LM">ROLE_LM</option>
                        <option selected="selected" value="ROLE_SE">ROLE_SE
                        </option>
                    </select>
            </tr>
            <tr>
                <td><input type="submit" class="btn btn-primary btn-md"
                           value="Create"/></td>
            </tr>
        </table>
    </div>
</form>
</body>
</html>