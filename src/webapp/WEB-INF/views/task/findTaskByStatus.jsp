<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Status your task</title>
</head>
<body>
<form action="/task/findTaskByStatus">
    Select task status:
    <select name="status">
    <option value="OPENED">OPENED</option>
    <option value="CLOSED">CLOSED</option>
    <option value="REOPENED">REOPENED</option>
    <option value="READY_FOR_TESTING">READY_FOR_TESTING</option>
</select><br>
    <p><c:if test="${not empty errorMap}">
        <c:forEach items="${errorMap}" var="error">
            ${error.value}<br>
        </c:forEach>
    </c:if></p>
    <br>
    <input type="submit" value="Find">
</form>
</body>
</html>
