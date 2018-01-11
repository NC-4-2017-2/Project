<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <style>
        p {
            color: red;
        }
    </style>
    <title>DeleteFromProject</title>
</head>
<body>
<table border="0">
    <c:forEach items="${userList}" var="user">
        <tr>
            <td>Last name:</td>
            <td>${user.lastName}</td>
        </tr>
        <tr>
            <td>First name:</td>
            <td>${user.firstName}</td>
        </tr>
        <tr>
            <td>Email:</td>
            <td>${user.email}</td>
        </tr>
        <tr>
            <td>Birth date:</td>
            <td>${user.dateOfBirth}</td>
        </tr>
        <tr>
            <td>User position:</td>
            <td>${user.jobTitle.name()}</td>
        </tr>
        <tr>
            <td>
                <form action="/project/deleteUserFromProject/project/${projectId}/user/${user.userId}/jobTitle/${user.jobTitle}"
                      method="post">
                    <button type="submit">Delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<%@include file="../errors/errorMap.jsp" %>
</body>
</html>