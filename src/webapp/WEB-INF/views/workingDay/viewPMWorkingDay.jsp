<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        p {
            color: red;
        }
    </style>
    <title>Show Working Days</title>
</head>
<body>
<c:forEach items="${workingDays}" var="workingDay">
    <a href="/workingDay/showUpdatePMWorkingDayStatus/${workingDay.workingDayId}">${workingDay.date}</a> <br>
</c:forEach>
</body>
</html>
