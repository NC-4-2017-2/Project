<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Find your task</title>
</head>
<body>
<form action="/task/findTaskByPriority">
    Select task priority:<select name="priority" required>
    <option value="CRITICAL">CRITICAL</option>
    <option value="HIGH">HIGH</option>
    <option value="NORMAL">NORMAL</option>
    <option value="LOW">LOW</option>
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
