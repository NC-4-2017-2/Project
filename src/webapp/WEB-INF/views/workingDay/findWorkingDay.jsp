<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        p {
            color: red;
        }
    </style>
    <title>Choose period</title>
</head>
<body>
<form action="/workingDay/viewWorkingDay">
    Start date: <input type="date" name="startDate"><br>
    End date: <input type="date" name="endDate"><br>
    <p><c:if test="${not empty errorMap}">
        <c:forEach items="${errorMap}" var="error">
            ${error.value}<br>
        </c:forEach>
    </c:if></p>
    <input type="submit" value="Find">
</form>
</body>
</html>
