<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Enter your data</title>
</head>
<style>
    p {
        color: red;
    }
</style>
<body>
<form action="/task/findTaskByFirstAndLastName">
    <table>
    <tr>
        <td>User last name:</td>
        <td><input type="text" name="lastName">
        </td>
    </tr>
    <tr>
        <td>User first name:</td>
        <td><input type="text" name="firstName">
        </td>
    </tr>
    </table>
    <%@include file="../errors/errorMap.jsp" %>
    <br>
    <input type="submit" value="Find">
</form>
</body>
</html>
